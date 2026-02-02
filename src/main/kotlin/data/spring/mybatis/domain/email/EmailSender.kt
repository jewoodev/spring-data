package data.spring.mybatis.domain.email

interface EmailSender {
    fun sendVerificationCode(emailAddr: String)
}