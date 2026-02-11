package data.spring.mybatis.adapter.out.email

import data.spring.mybatis.domain.email.EmailContent
import data.spring.mybatis.domain.email.EmailSender
import data.spring.mybatis.domain.email.VerificationEmailContent
import data.spring.mybatis.domain.member.Email
import data.spring.mybatis.domain.member.EmailVerifierCache

class SimpleEmailSender(
    val evCache: EmailVerifierCache
) : EmailSender {
    override fun sendVerificationCode(email: Email) {
        val emailContent = VerificationEmailContent()

        evCache.setVerificationCode(emailAddr = email.value, verificationCode = emailContent.verificationCode)
        
        return sendEmail(emailAddr = email.value, emailContent = emailContent)
    }

    private fun sendEmail(emailAddr: String, emailContent: EmailContent) {
        // 메일 전송에 실패하면 예외 발생
    }
}