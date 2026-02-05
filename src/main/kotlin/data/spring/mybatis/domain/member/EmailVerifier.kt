package data.spring.mybatis.domain.member

interface EmailVerifier {
    fun verify(email: Email, verificationCode: String): Boolean
}