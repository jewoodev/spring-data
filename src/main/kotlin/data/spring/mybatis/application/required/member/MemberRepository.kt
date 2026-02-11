package data.spring.mybatis.application.required.member

import data.spring.mybatis.domain.member.Member

interface MemberRepository {
    fun save(member: Member): Int
    fun saveAll(members: List<Member>): Int

    fun update(member: Member): Int
    fun leave(member: Member): Int

    fun deleteAll(): Int

    fun findById(memberId: Long): Member?
    fun findByUsername(username: String): Member?
    fun findByEmail(emailAddr: String): Member?
    fun checkDuplication(username: String, emailAddr: String): Boolean
    fun findAll(): List<Member>
}