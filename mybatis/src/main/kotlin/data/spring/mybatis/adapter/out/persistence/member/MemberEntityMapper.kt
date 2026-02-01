package data.spring.mybatis.adapter.out.persistence.member

import data.spring.mybatis.adapter.out.persistence.member.entity.MemberEntity
import org.apache.ibatis.annotations.Mapper

@Mapper
interface MemberEntityMapper {
    fun save(entity: MemberEntity)
    fun saveAll(entities: List<MemberEntity>): Int

    fun findById(memberId: Long): MemberEntity?
    fun findByUsername(username: String): MemberEntity?
    fun findByEmail(emailAddr: String): MemberEntity?
    fun findDuplicated(username: String, emailAddr: String): MemberEntity?
    fun findAll(): List<MemberEntity>

    fun update(entity: MemberEntity)
    fun leave(entity: MemberEntity)

    fun deleteAll(): Int
}