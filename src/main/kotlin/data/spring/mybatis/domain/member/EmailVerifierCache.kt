package data.spring.mybatis.domain.member

interface EmailVerifierCache {
    fun getVerificationCode(emailAddr: String): String
    fun setVerificationCode(emailAddr: String, verificationCode: String)
}