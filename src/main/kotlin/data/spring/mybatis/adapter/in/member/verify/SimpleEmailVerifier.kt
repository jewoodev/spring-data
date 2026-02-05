package data.spring.mybatis.adapter.`in`.member.verify

import data.spring.mybatis.domain.member.Email
import data.spring.mybatis.domain.member.EmailVerifier
import data.spring.mybatis.domain.member.EmailVerifierCache

class SimpleEmailVerifier(
    val evCache: EmailVerifierCache,
) : EmailVerifier {
    override fun verify(email: Email, verificationCode: String): Boolean {
        return evCache.getVerificationCode(email.value) == verificationCode
    }
}