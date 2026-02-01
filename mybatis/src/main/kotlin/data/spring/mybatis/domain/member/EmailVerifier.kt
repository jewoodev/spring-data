package data.spring.mybatis.domain.member

interface EmailVerifier {
    fun verify(emailAddr: String, verificationCode: String): Boolean
}