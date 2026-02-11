package data.spring.mybatis.application.provided.member.dto

data class MemberCreateCommand(
    val username: String,
    val password: String,
    val email: String
)
