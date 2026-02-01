package data.spring.mybatis.application.service.member

import data.spring.mybatis.application.exception.NoDataFoundException
import data.spring.mybatis.application.provided.member.MemberDuplicationVerifier
import data.spring.mybatis.application.provided.member.MemberUseCase
import data.spring.mybatis.application.provided.member.request.EmailVerifyRequest
import data.spring.mybatis.application.required.member.MemberRepository
import data.spring.mybatis.domain.email.EmailSender
import data.spring.mybatis.domain.member.EmailVerifier
import data.spring.mybatis.domain.member.Member
import data.spring.mybatis.domain.member.PasswordEncoder
import data.spring.mybatis.domain.member.request.MemberCreateRequest
import data.spring.mybatis.domain.member.request.VfcCodeSendRequest

class MemberService(
    val memberRepository: MemberRepository,
    val passwordEncoder: PasswordEncoder,
    val memberDuplicationVerifier: MemberDuplicationVerifier,
    val emailVerifier: EmailVerifier,
    val emailSender: EmailSender
) : MemberUseCase {
    override fun register(createRequest: MemberCreateRequest) {
        memberDuplicationVerifier.verify(createRequest.username, createRequest.email)

        Member.register(createRequest, passwordEncoder).let(this.memberRepository::save)
    }

    override fun sendVerificationCode(codeSendRequest: VfcCodeSendRequest) {
        val member = memberRepository.findById(codeSendRequest.memberId)
            ?: throw NoDataFoundException("Member not found with id: ${codeSendRequest.memberId}.")

        emailSender.sendVerificationCode(member.email)
    }

    override fun verify(verifyRequest: EmailVerifyRequest) {
        val member = memberRepository.findById(verifyRequest.memberId)
            ?: throw NoDataFoundException("이메일 인증 과정에서 있을 수 없는 회원 식별자 값이 감지되었습니다: ${verifyRequest.memberId}.")
        member
        if (emailVerifier.verify(member.email, verifyRequest.verificationCode)) {
            member.activate().let(this.memberRepository::update)
        } else throw IllegalArgumentException("이메일 인증 코드가 올바르지 않습니다.")
    }

    override fun findById(memberId: Long): Member? {
        return this.memberRepository.findById(memberId) ?: throw NoDataFoundException("Member not found with id: ${memberId}.")
    }

    override fun findAll(): List<Member> {
        return this.memberRepository.findAll().ifEmpty { throw NoDataFoundException("No members found.") }
    }

    override fun changePassword(member: Member, newPassword: String) {
        member.changePassword(newPassword, passwordEncoder).let(this.memberRepository::update)
    }

    override fun leave(member: Member) {
        member.leave().let(memberRepository::leave)
    }

    override fun deleteAll(): Int {
        return this.memberRepository.deleteAll()
    }
}