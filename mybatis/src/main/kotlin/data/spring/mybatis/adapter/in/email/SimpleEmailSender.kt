package data.spring.mybatis.adapter.`in`.email

import data.spring.mybatis.domain.email.EmailSender
import data.spring.mybatis.domain.email.createVerificationCode
import data.spring.mybatis.domain.email.createVerificationEmail
import data.spring.mybatis.domain.member.EmailVerifierCache

class SimpleEmailSender(
    val evCache: EmailVerifierCache
) : EmailSender {
    override fun sendVerificationCode(emailAddr: String) {
        val vfcCode = createVerificationCode()
        evCache.setVerificationCode(emailAddr, vfcCode)
        val emailContent = createVerificationEmail(vfcCode)
        // send email
    }
}