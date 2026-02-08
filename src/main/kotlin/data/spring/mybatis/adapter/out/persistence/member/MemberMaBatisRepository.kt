package data.spring.mybatis.adapter.out.persistence.member

import data.spring.mybatis.application.required.member.MemberRepository
import data.spring.mybatis.domain.member.Member

class MemberMaBatisRepository(
    val memberMapper: MemberMapper
) : MemberRepository {
    override fun save(member: Member) {
         memberMapper.save(member)
    }

    override fun saveAll(members: List<Member>): Int {
        return memberMapper.saveAll(members)
    }

    override fun update(member: Member) {
        return memberMapper.update(member)
    }

    override fun leave(member: Member) {
        memberMapper.leave(member)
    }

    override fun deleteAll(): Int {
        return memberMapper.truncate()
    }

    override fun findById(memberId: Long): Member? {
        return memberMapper.findById(memberId)
    }

    override fun findByUsername(username: String): Member? {
        return memberMapper.findByUsername(username)
    }

    override fun findByEmail(emailAddr: String): Member? {
        return memberMapper.findByEmail(emailAddr)
    }

    override fun checkDuplication(username: String, emailAddr: String): Boolean {
        return memberMapper.findDuplicated(username, emailAddr) != null
    }

    override fun findAll(): List<Member> {
        return memberMapper.findAll()
    }
}