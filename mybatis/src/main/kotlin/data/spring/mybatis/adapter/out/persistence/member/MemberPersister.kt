package data.spring.mybatis.adapter.out.persistence.member

import data.spring.mybatis.application.required.member.MemberRepository
import data.spring.mybatis.domain.member.Member

class MemberPersister(
    val memberMapper: MemberMapper
) : MemberRepository {
    override fun save(member: Member) {
         this.memberMapper.save(member)
    }

    override fun saveAll(members: List<Member>): Int {
        return this.memberMapper.saveAll(members)
    }

    override fun findById(memberId: Long): Member? {
        return this.memberMapper.findById(memberId)
    }

    override fun findByUsername(username: String): Member? {
        return this.memberMapper.findByUsername(username)
    }

    override fun findByEmail(emailAddr: String): Member? {
        return this.memberMapper.findByEmail(emailAddr)
    }

    override fun findDuplicated(username: String, emailAddr: String): Member? {
        return this.memberMapper.findDuplicated(username, emailAddr)
    }

    override fun findAll(): List<Member> {
        return this.memberMapper.findAll()
    }

    override fun update(member: Member) {
        return this.memberMapper.update(member)
    }

    override fun leave(member: Member) {
        this.memberMapper.leave(member)
    }

    override fun deleteAll(): Int {
        return this.memberMapper.deleteAll()
    }
}