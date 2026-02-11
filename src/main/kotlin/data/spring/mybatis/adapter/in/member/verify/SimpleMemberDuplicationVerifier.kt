package data.spring.mybatis.adapter.`in`.member.verify

import data.spring.mybatis.domain.member.MemberDuplicationVerifier
import data.spring.mybatis.application.required.member.MemberRepository

class SimpleMemberDuplicationVerifier(
    val memberRepository: MemberRepository
) : MemberDuplicationVerifier {
    override fun verify(username: String, emailAddr: String): Boolean {
        return memberRepository.checkDuplication(username, emailAddr)
    }
}
