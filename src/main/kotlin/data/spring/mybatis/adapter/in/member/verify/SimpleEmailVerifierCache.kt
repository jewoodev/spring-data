package data.spring.mybatis.adapter.`in`.member.verify

import data.spring.mybatis.domain.member.EmailVerifierCache

class SimpleEmailVerifierCache : EmailVerifierCache {
    val evCache: MutableMap<String, String> = mutableMapOf()

    override fun getVerificationCode(emailAddr: String): String {
        val verificationNum = evCache[emailAddr] ?: throw IllegalStateException("이메일 인증번호가 존재하지 않습니다. 이메일을 다시 수신해주세요.")
        return verificationNum
    }

    override fun setVerificationCode(emailAddr: String, verificationCode: String) {
        evCache[emailAddr] = verificationCode
    }
}