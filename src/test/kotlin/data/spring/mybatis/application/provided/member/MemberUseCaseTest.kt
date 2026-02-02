package data.spring.mybatis.application.provided.member

import data.spring.mybatis.IntegrationTestSupport
import data.spring.mybatis.application.provided.member.request.EmailVerifyRequest
import data.spring.mybatis.domain.member.MemberFixture
import data.spring.mybatis.domain.member.Role
import data.spring.mybatis.domain.member.request.MemberCreateRequest
import data.spring.mybatis.domain.member.request.VfcCodeSendRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

class MemberUseCaseTest : IntegrationTestSupport() {
    @AfterEach
    fun tearDown() {
        super.memberUseCase.deleteAll()
    }

    @Test
    fun registerMemberSuccessfully() {
        val sut = super.memberUseCase
        val member = MemberCreateRequest(username = "testuser", password = "testPassword", email = "test@example.com")

        sut.register(member)

        val registeredMember = sut.findById(1L)
        assertThat(registeredMember!!.username).isEqualTo(member.username)
        assertThat(registeredMember.email).isEqualTo(member.email)
    }

    @Test
    fun sendVerificationCodeSuccessfully() {
        val sut = super.memberUseCase
        sut.register(MemberFixture.createMemberCreateRequest())
        val registered = sut.findById(1L)!!

        sut.sendVerificationCode(VfcCodeSendRequest(registered.memberId!!))

        val cachedCode = super.evcCache.getVerificationCode(registered.email)
        assertThat(cachedCode).isNotNull
    }

    @Test
    fun verifySuccessfully() {
        val sut = super.memberUseCase
        sut.register(MemberFixture.createMemberCreateRequest())
        val registered = sut.findById(1L)!!
        sut.sendVerificationCode(VfcCodeSendRequest(registered.memberId!!))
        val verifyRequest = EmailVerifyRequest(
            registered.memberId, super.evcCache.getVerificationCode(registered.email)!!
        )

        sut.verify(verifyRequest)

        val verifiedMember = sut.findById(registered.memberId!!)
        assertThat(verifiedMember!!.role).isEqualTo(Role.BUYER)
    }

    @Test
    fun changePasswordSuccessfully() {
        val sut = super.memberUseCase
        val passwordEncoder = MemberFixture.createPasswordEncoder()
        sut.register(MemberFixture.createMemberCreateRequest())
        val registered = sut.findById(1L)!!
        val newPassword = "newTestPassword"

        sut.changePassword(registered, newPassword)

        val changedMember = sut.findById(registered.memberId!!)!!
        assertThat(changedMember.password)
            .isEqualTo(passwordEncoder.encode(newPassword))
    }
}