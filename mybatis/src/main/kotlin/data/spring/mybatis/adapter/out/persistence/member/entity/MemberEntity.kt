package data.spring.mybatis.adapter.out.persistence.member.entity

import data.spring.mybatis.domain.member.Member
import data.spring.mybatis.domain.member.Role
import java.time.LocalDateTime

class MemberEntity(
    val memberId: Long? = null,
    val username: String,
    val password: String,
    val email: String,
    val role: Role,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val leftAt: LocalDateTime?
) {
    companion object {
        fun fromDomain(member: Member): MemberEntity {
            return MemberEntity(
                memberId = member.memberId,
                username = member.username,
                password = member.password,
                email = member.email,
                role = member.role,
                createdAt = member.createdAt,
                updatedAt = member.updatedAt,
                leftAt = member.leftAt
            )
        }
    }

    fun toDomain(): Member {
        return Member(
            memberId = this.memberId!!,
            username = this.username,
            password = this.password,
            email = this.email,
            role = this.role,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            leftAt = this.leftAt
        )
    }
}