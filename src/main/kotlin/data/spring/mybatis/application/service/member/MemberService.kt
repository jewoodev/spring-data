package data.spring.mybatis.application.service.member

import data.spring.mybatis.application.exception.NoDataFoundException
import data.spring.mybatis.domain.member.MemberDuplicationVerifier
import data.spring.mybatis.application.provided.member.MemberUseCase
import data.spring.mybatis.application.required.member.MemberRepository
import data.spring.mybatis.domain.email.EmailSender
import data.spring.mybatis.domain.member.EmailVerifier
import data.spring.mybatis.domain.member.Member
import data.spring.mybatis.domain.member.PasswordEncoder
import data.spring.mybatis.application.provided.member.dto.EmailVerifyCommand
import data.spring.mybatis.application.provided.member.dto.MemberCreateCommand
import data.spring.mybatis.application.provided.member.dto.VfcCodeSendCommand
import data.spring.mybatis.domain.member.Email
import data.spring.mybatis.domain.member.Password
import data.spring.mybatis.domain.member.Username

class MemberService(
    val memberRepository: MemberRepository,
    val passwordEncoder: PasswordEncoder,
    val memberDuplicationVerifier: MemberDuplicationVerifier,
    val emailVerifier: EmailVerifier,
    val emailSender: EmailSender
) : MemberUseCase {
    override fun register(createCommand: MemberCreateCommand) {
        Member.register(
            username = Username(createCommand.username),
            password = Password(createCommand.password),
            email = Email(createCommand.email),
            passwordEncoder = passwordEncoder,
            duplicationVerifier = memberDuplicationVerifier
        ).let { this.memberRepository.save(it) }
    }

    override fun sendVerificationCode(codeSendCommand: VfcCodeSendCommand) {
        val member = memberRepository.findById(codeSendCommand.memberId)
            ?: throw NoDataFoundException("Member not found with id: ${codeSendCommand.memberId}.")

        member.sendVerificationCode(emailSender)
    }

    override fun verify(verifyCommand: EmailVerifyCommand) {
        val member = memberRepository.findById(verifyCommand.memberId)
            ?: throw NoDataFoundException("이메일 인증 과정에서 있을 수 없는 회원 식별자 값이 감지되었습니다: ${verifyCommand.memberId}.")

        member.verify(emailVerifier, verifyCommand.verificationCode)
            .let { this.memberRepository.update(it) }
    }

    override fun findById(memberId: Long): Member? {
        return this.memberRepository.findById(memberId)
            ?: throw NoDataFoundException("Member not found with id: ${memberId}.")
    }

    override fun findAll(): List<Member> {
        return this.memberRepository.findAll().ifEmpty { throw NoDataFoundException("No members found.") }
    }

    override fun changePassword(member: Member, newPassword: String) {
        member.changePassword(newPassword, passwordEncoder).let { this.memberRepository.update(it) }
    }

    override fun leave(member: Member) {
        member.leave().let { this.memberRepository.leave(it) }
    }

    override fun deleteAll(): Int {
        return this.memberRepository.deleteAll()
    }
}