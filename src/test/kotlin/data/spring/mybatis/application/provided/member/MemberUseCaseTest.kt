package data.spring.mybatis.application.provided.member

import data.spring.mybatis.IntegrationTestSupport
import data.spring.mybatis.application.exception.HackingDoubtException
import data.spring.mybatis.application.exception.NoDataFoundException
import data.spring.mybatis.application.provided.member.dto.EmailVerifyCommand
import data.spring.mybatis.application.provided.member.dto.MemberCreateCommand
import data.spring.mybatis.application.provided.member.dto.VfcCodeSendCommand
import data.spring.mybatis.domain.member.MemberDuplicationException
import data.spring.mybatis.domain.member.Role
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

class MemberUseCaseTest : IntegrationTestSupport() {
    private val sut by lazy { super.memberUseCase }

    @AfterEach
    fun tearDown() {
        sut.deleteAll()
    }

    @Test
    fun `register member successfully`() {
        // given
        val createCommand = createMemberCommand()

        // when
        sut.register(createCommand)

        // then
        val registeredMembers = sut.findAll()
        assertThat(registeredMembers).hasSize(1)
        assertThat(registeredMembers[0].username.value).isEqualTo(createCommand.username)
        assertThat(registeredMembers[0].email.value).isEqualTo(createCommand.email)
    }

    @Test
    fun `throw exception when registering with duplicate username or email`() {
        // given
        val createCommand1 = createMemberCommand(username = "duplicateUser", email = "duplicate@example.com")
        val createCommand2 = createMemberCommand(username = "otherUser", email = "duplicate@example.com")
        sut.register(createCommand1)

        // when & then
        assertThatThrownBy { sut.register(createCommand2) }
            .isInstanceOf(MemberDuplicationException::class.java)
    }

    @Test
    fun `send verification code successfully`() {
        // given
        val createCommand = createMemberCommand(username = "testuser", email = "test@example.com")
        sut.register(createCommand)
        val registered = sut.findById(1L)

        // when
        sut.sendVerificationCode(VfcCodeSendCommand(registered.memberId!!))

        // then
        val cachedCode = evcCache.getVerificationCode(registered.email.value)
        assertThat(cachedCode).isNotNull
    }

    @Test
    fun `throw exception when sending verification code from non-existent member`() {
        // given
        val nonExistentMemberId = 9999L

        // when & then
        assertThatThrownBy { sut.sendVerificationCode(VfcCodeSendCommand(nonExistentMemberId)) }
            .isInstanceOf(HackingDoubtException::class.java)
            .hasMessageContaining("메일 인증 코드 전송 중에 있을 수 없는 회원 식별자 값이 감지되었습니다: $nonExistentMemberId.")
    }

    @Test
    fun `verify email successfully`() {
        // given
        val createCommand = createMemberCommand(username = "testuser", email = "test@example.com")
        sut.register(createCommand)
        val registered = sut.findById(1L)

        sut.sendVerificationCode(VfcCodeSendCommand(registered.memberId!!))
        val verificationCode = super.evcCache.getVerificationCode(registered.email.value)
        val verifyCommand = EmailVerifyCommand(memberId = registered.memberId, verificationCode = (verificationCode))

        // when
        sut.verify(verifyCommand)

        // then
        val verifiedMember = sut.findById(registered.memberId)
        assertThat(verifiedMember.role).isEqualTo(Role.BUYER)
    }

    @Test
    fun `throw exception when verifying email with incorrect code`() {
        // given
        val createCommand = createMemberCommand(username = "testuser", email = "test@example.com")
        sut.register(createCommand)
        val registered = sut.findById(1L)
        sut.sendVerificationCode(VfcCodeSendCommand(registered.memberId!!))
        val incorrectCode = "9999999" // code got impossible length (7)
        val verifyCommand = EmailVerifyCommand(memberId = registered.memberId, verificationCode = incorrectCode)

        // when & then
        assertThatThrownBy { sut.verify(verifyCommand) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("이메일 인증 코드가 올바르지 않습니다.")
    }

    @Test
    fun `throw exception when verifying email of non-existent member`() {
        // given
        val nonExistentMemberId = 9999L
        val verifyCommand = EmailVerifyCommand(memberId = nonExistentMemberId, verificationCode = "123456")

        // when & then
        assertThatThrownBy { sut.verify(verifyCommand) }
            .isInstanceOf(HackingDoubtException::class.java)
            .hasMessageContaining("이메일 인증 과정에서 있을 수 없는 회원 식별자 값이 감지되었습니다")
    }

    @Test
    fun `find member by id successfully`() {
        // given
        val createCommand = createMemberCommand(username = "testuser", email = "test@example.com")
        sut.register(createCommand)
        val registered = sut.findById(1L)

        // when
        val foundMember = sut.findById(registered.memberId!!)

        // then
        assertThat(foundMember).isNotNull
        assertThat(foundMember.memberId).isEqualTo(registered.memberId)
    }

    @Test
    fun `throw exception when finding non-existent member by id`() {
        // given
        val nonExistentMemberId = 9999L

        // when & then
        assertThatThrownBy { sut.findById(nonExistentMemberId) }
            .isInstanceOf(NoDataFoundException::class.java)
            .hasMessageContaining("회원 중 해당 식별자를 갖는 회원이 없습니다: $nonExistentMemberId.")
    }

    @Test
    fun `find all members successfully`() {
        // given
        val createCommand1 = createMemberCommand(username = "testuser1", email = "test1@example.com")
        val createCommand2 = createMemberCommand(username = "testuser2", email = "test2@example.com")
        for (c in listOf(createCommand1, createCommand2)) {
            sut.register(c)
        }

        // when
        val members = sut.findAll()

        // then
        assertThat(members).hasSize(2)
    }

    @Test
    fun `throw exception when finding all members and none exist`() {
        // when & then
        assertThatThrownBy { sut.findAll() }
            .isInstanceOf(NoDataFoundException::class.java)
            .hasMessage("No members found.")
    }

    @Test
    fun `change password successfully`() {
        // given
        val createCommand = createMemberCommand(username = "testuser", email = "test@example.com")
        sut.register(createCommand)
        val registered = sut.findById(1L)
        val newPassword = "newTestPassword"

        // when
        sut.changePassword(registered.memberId!!, newPassword)

        // then
        val changedMember = sut.findById(registered.memberId)
        assertThat(passwordEncoder.matches(newPassword, changedMember.password.value)).isTrue()
    }

    @Test
    fun `leave member successfully`() {
        // given
        val createCommand = createMemberCommand(username = "testuser", email = "test@example.com")
        sut.register(createCommand)
        val registered = sut.findById(1L)

        // when
        sut.leave(registered.memberId!!)

        // then
        val leftMember = sut.findById(registered.memberId)
        assertThat(leftMember.leftAt).isNotNull()
    }

    @Test
    fun `delete all members successfully`() {
        // given
        val createCommand1 = createMemberCommand(username = "testuser1", email = "test1@example.com")
        val createCommand2 = createMemberCommand(username = "testuser2", email = "test2@example.com")
        for (c in listOf(createCommand1, createCommand2)) {
            sut.register(c)
        }

        // when
        val deletedCount = sut.deleteAll()

        // then
        assertThat(deletedCount).isEqualTo(2)
        assertThatThrownBy { sut.findAll() }.isInstanceOf(NoDataFoundException::class.java)
    }

    private fun createMemberCommand(
        username: String = "testuser",
        email: String = "test@example.com"
    ) = MemberCreateCommand(
        username = username,
        password = "testPassword",
        email = email
    )
}