package data.spring.mybatis.adapter.`in`.member.verify

import data.spring.mybatis.application.provided.member.MemberDuplicationVerifier
import data.spring.mybatis.application.required.member.MemberRepository

class SimpleMemberDuplicationVerifier(
    val memberRepository: MemberRepository
) : MemberDuplicationVerifier {
    override fun verify(username: String, emailAddr: String) {
        this.memberRepository.findDuplicated(username, emailAddr)?.let { throw MemberDuplicationException("이미 가입된 이메일입니다.") }
    }
}

class MemberDuplicationException(message: String) : RuntimeException(message)
