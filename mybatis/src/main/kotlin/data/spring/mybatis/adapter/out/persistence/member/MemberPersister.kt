package data.spring.mybatis.adapter.out.persistence.member

import data.spring.mybatis.adapter.out.persistence.member.entity.MemberEntity
import data.spring.mybatis.application.required.member.MemberRepository
import data.spring.mybatis.domain.member.Member

class MemberPersister(
    val memberEntityMapper: MemberEntityMapper
) : MemberRepository {
    override fun save(member: Member) {
         this.memberEntityMapper.save(MemberEntity.fromDomain(member))
    }

    override fun saveAll(members: List<Member>): Int {
        return this.memberEntityMapper.saveAll(members.map(MemberEntity::fromDomain))
    }

    override fun findById(memberId: Long): Member? {
        return this.memberEntityMapper.findById(memberId)?.toDomain()
    }

    override fun findByUsername(username: String): Member? {
        return this.memberEntityMapper.findByUsername(username)?.toDomain()
    }

    override fun findByEmail(emailAddr: String): Member? {
        return this.memberEntityMapper.findByEmail(emailAddr)?.toDomain()
    }

    override fun findDuplicated(username: String, emailAddr: String): Member? {
        return this.memberEntityMapper.findDuplicated(username, emailAddr)?.toDomain()
    }

    override fun findAll(): List<Member> {
        return this.memberEntityMapper.findAll().map { it.toDomain() }
    }

    override fun update(member: Member) {
        return this.memberEntityMapper.update(MemberEntity.fromDomain(member))
    }

    override fun leave(member: Member) {
        this.memberEntityMapper.leave(MemberEntity.fromDomain(member))
    }

    override fun deleteAll(): Int {
        return this.memberEntityMapper.deleteAll()
    }
}