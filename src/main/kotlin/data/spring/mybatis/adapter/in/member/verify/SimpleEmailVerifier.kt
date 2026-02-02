package data.spring.mybatis.adapter.`in`.member.verify

import data.spring.mybatis.domain.member.EmailVerifier
import data.spring.mybatis.domain.member.EmailVerifierCache

class SimpleEmailVerifier(
    val evCache: EmailVerifierCache,
) : EmailVerifier {
    override fun verify(emailAddr: String, verificationCode: String): Boolean {
        return evCache.getVerificationCode(emailAddr) == verificationCode
    }
}