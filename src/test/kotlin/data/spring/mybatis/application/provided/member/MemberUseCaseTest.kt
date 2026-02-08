package data.spring.mybatis.application.provided.member

import data.spring.mybatis.IntegrationTestSupport
import data.spring.mybatis.domain.member.MemberDuplicationException
import data.spring.mybatis.application.exception.NoDataFoundException
import data.spring.mybatis.application.provided.member.dto.EmailVerifyCommand
import data.spring.mybatis.application.provided.member.dto.MemberCreateCommand
import data.spring.mybatis.application.provided.member.dto.VfcCodeSendCommand
import data.spring.mybatis.domain.member.Member
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
        val registeredMember = sut.findAll().first { it?.username?.value == createCommand.username }
        assertThat(registeredMember!!.username.value).isEqualTo(createCommand.username)
        assertThat(registeredMember.email.value).isEqualTo(createCommand.email)
    }

    @Test
    fun `throw exception when registering with duplicate username or email`() {
        // given
        val username = "duplicateUser"
        val email = "duplicate@example.com"
        registerMember(username = username, email = email)

        // when & then
        assertThatThrownBy { sut.register(createMemberCommand(username = username, email = "other@example.com")) }
            .isInstanceOf(MemberDuplicationException::class.java)

        assertThatThrownBy { sut.register(createMemberCommand(username = "otherUser", email = email)) }
            .isInstanceOf(MemberDuplicationException::class.java)
    }

    @Test
    fun `send verification code successfully`() {
        // given
        val registered = registerMember()

        // when
        sut.sendVerificationCode(VfcCodeSendCommand(registered.memberId!!))

        // then
        val cachedCode = super.evcCache.getVerificationCode(registered.email.value)
        assertThat(cachedCode).isNotNull
    }

    @Test
    fun `throw exception when sending verification code to non-existent member`() {
        // given
        val nonExistentMemberId = 9999L

        // when & then
        assertThatThrownBy { sut.sendVerificationCode(VfcCodeSendCommand(nonExistentMemberId)) }
            .isInstanceOf(NoDataFoundException::class.java)
            .hasMessageContaining("Member not found with id: $nonExistentMemberId.")
    }

    @Test
    fun `verify email successfully`() {
        // given
        val registered = registerMember()
        sut.sendVerificationCode(VfcCodeSendCommand(registered.memberId!!))
        val verificationCode = super.evcCache.getVerificationCode(registered.email.value)
        val verifyCommand = EmailVerifyCommand(memberId = registered.memberId, verificationCode = (verificationCode))

        // when
        sut.verify(verifyCommand)

        // then
        val verifiedMember = sut.findById(registered.memberId!!)
        assertThat(verifiedMember!!.role).isEqualTo(Role.BUYER)
    }

    @Test
    fun `throw exception when verifying email with incorrect code`() {
        // given
        val registered = registerMember()
        sut.sendVerificationCode(VfcCodeSendCommand(registered.memberId!!))
        val incorrectCode = "9999999"
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
            .isInstanceOf(NoDataFoundException::class.java)
            .hasMessageContaining("이메일 인증 과정에서 있을 수 없는 회원 식별자 값이 감지되었습니다")
    }

    @Test
    fun `find member by id successfully`() {
        // given
        val registered = registerMember()

        // when
        val foundMember = sut.findById(registered.memberId!!)

        // then
        assertThat(foundMember).isNotNull
        assertThat(foundMember!!.memberId).isEqualTo(registered.memberId)
    }

    @Test
    fun `throw exception when finding non-existent member by id`() {
        // given
        val nonExistentMemberId = 9999L

        // when & then
        assertThatThrownBy { sut.findById(nonExistentMemberId) }
            .isInstanceOf(NoDataFoundException::class.java)
            .hasMessageContaining("Member not found with id: $nonExistentMemberId.")
    }

    @Test
    fun `find all members successfully`() {
        // given
        registerMember(username = "user1", email = "user1@example.com")
        registerMember(username = "user2", email = "user2@example.com")

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
        val passwordEncoder = super.passwordEncoder
        val registered = registerMember()
        val newPassword = "newTestPassword"

        // when
        sut.changePassword(registered, newPassword)

        // then
        val changedMember = sut.findById(registered.memberId!!)!!
        assertThat(passwordEncoder.matches(newPassword, changedMember.password.value)).isTrue()
    }

    @Test
    fun `leave member successfully`() {
        // given
        val registered = registerMember()

        // when
        sut.leave(registered)

        // then
        val leftMember = sut.findById(registered.memberId!!)!!
        assertThat(leftMember.leftAt).isNotNull()
    }

    @Test
    fun `delete all members successfully`() {
        // given
        registerMember(username = "user1", email = "user1@example.com")
        registerMember(username = "user2", email = "user2@example.com")

        // when
        val deletedCount = sut.deleteAll()

        // then
        assertThat(deletedCount).isEqualTo(2)
        assertThatThrownBy { sut.findAll() }.isInstanceOf(NoDataFoundException::class.java)
    }

    private fun registerMember(
        username: String = "testuser",
        email: String = "test@example.com"
    ): Member {
        val command = createMemberCommand(username, email)
        sut.register(command)
        return sut.findAll().first { it?.username?.value == username }!!
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