package data.spring.mybatis.domain.member

import data.spring.mybatis.domain.clock
import data.spring.mybatis.domain.email.EmailSender
import data.spring.mybatis.domain.member.Role.UNVERIFIED
import java.time.LocalDateTime

data class Member constructor(
    val memberId: Long? = null,
    val username: Username,
    var password: Password,
    val email: Email,
    val role: Role = UNVERIFIED,
    val createdAt: LocalDateTime = LocalDateTime.now(clock()),
    val updatedAt: LocalDateTime = LocalDateTime.now(clock()),
    val leftAt: LocalDateTime? = null,
) {
    fun sendVerificationCode(emailSender: EmailSender) {
        emailSender.sendVerificationCode(email)
    }

    fun verify(verifier: EmailVerifier, verificationCode: String): Member {
        require(verifier.verify(email, verificationCode)) { "이메일 인증 코드가 올바르지 않습니다." }
        return this.activate()
    }

    fun activate(): Member {
        check(role == UNVERIFIED) { "이미 활성화된 회원입니다." }
        return copy(role = Role.BUYER, updatedAt = LocalDateTime.now(clock()))
    }

    fun changePassword(newPassword: String, passwordEncoder: PasswordEncoder): Member {
        return copy(password = Password(passwordEncoder.encode(newPassword)),
                updatedAt = LocalDateTime.now(clock()))
    }

    fun leave(): Member {
        return copy(leftAt = LocalDateTime.now(clock()),
                updatedAt = LocalDateTime.now(clock()))
    }

    companion object {
        fun register(
            username: String,
            password: String,
            email: String,
            passwordEncoder: PasswordEncoder,
            duplicationVerifier: MemberDuplicationVerifier
        ): Member {
            return if (checkDuplication(username, email, duplicationVerifier)) {
                throw MemberDuplicationException("아이디나 이메일이 중복되었습니다.")
            } else {
                Member(
                    username = Username(username),
                    password = Password(passwordEncoder.encode(password)),
                    email = Email(email)
                )
            }
        }

        fun checkDuplication(
            username: String,
            email: String,
            memberDuplicationVerifier: MemberDuplicationVerifier
        ): Boolean {
            return memberDuplicationVerifier.verify(username, email)
        }
    }
}