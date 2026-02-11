package data.spring.mybatis.adapter.out.persistence.member

import data.spring.mybatis.domain.member.Member
import org.apache.ibatis.annotations.Mapper

@Mapper
interface MemberMapper {
    fun save(member: Member): Int
    fun saveAll(members: List<Member>): Int

    fun update(member: Member): Int
    fun leave(member: Member): Int

    fun truncate(): Int

    fun findById(memberId: Long): Member?
    fun findByUsername(username: String): Member?
    fun findByEmail(emailAddr: String): Member?
    fun findDuplicated(username: String, emailAddr: String): Member?
    fun findAll(): List<Member>
}