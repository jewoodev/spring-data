package data.spring.mybatis.adapter.out.email

import data.spring.mybatis.domain.email.EmailSender
import data.spring.mybatis.domain.email.createVerificationCode
import data.spring.mybatis.domain.email.createVerificationEmail
import data.spring.mybatis.domain.member.Email
import data.spring.mybatis.domain.member.EmailVerifierCache

class SimpleEmailSender(
    val evCache: EmailVerifierCache
) : EmailSender {
    override fun sendVerificationCode(email: Email) {
        val vfcCode = createVerificationCode()
        evCache.setVerificationCode(email.value, vfcCode)
        val emailContent = createVerificationEmail(vfcCode)
        // send email
    }
}