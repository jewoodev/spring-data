package data.spring.mybatis.application.service.member

import data.spring.mybatis.application.exception.HackingDoubtException
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
    override fun register(createCommand: MemberCreateCommand): Int {
        return Member.register(
            username = createCommand.username,
            password = createCommand.password,
            email = createCommand.email,
            passwordEncoder = passwordEncoder,
            duplicationVerifier = memberDuplicationVerifier
        ).let { memberRepository.save(it) }
    }

    override fun sendVerificationCode(codeSendCommand: VfcCodeSendCommand) {
        val member = memberRepository.findById(codeSendCommand.memberId)
            ?: throw HackingDoubtException("메일 인증 코드 전송 중에 있을 수 없는 회원 식별자 값이 감지되었습니다: ${codeSendCommand.memberId}.")

        member.sendVerificationCode(emailSender)
    }

    override fun verify(verifyCommand: EmailVerifyCommand): Int {
        val member = memberRepository.findById(verifyCommand.memberId)
            ?: throw HackingDoubtException("이메일 인증 과정에서 있을 수 없는 회원 식별자 값이 감지되었습니다: ${verifyCommand.memberId}.")

        return member.verify(emailVerifier, verifyCommand.verificationCode)
            .let { memberRepository.update(it) }
    }

    override fun findById(memberId: Long): Member {
        return memberRepository.findById(memberId)
            ?: throw NoDataFoundException("회원 중 해당 식별자를 갖는 회원이 없습니다: ${memberId}.")
    }

    override fun findAll(): List<Member> {
        return memberRepository.findAll().ifEmpty { throw NoDataFoundException("No members found.") }
    }

    override fun changePassword(memberId: Long, newPassword: String): Int {
        val member = findById(memberId)

        return member.changePassword(newPassword, passwordEncoder)
            .let { memberRepository.update(it) }
    }

    override fun leave(memberId: Long): Int {
        val member = findById(memberId)

        return member.leave().let { memberRepository.leave(it) }
    }

    override fun deleteAll(): Int {
        return memberRepository.deleteAll()
    }
}