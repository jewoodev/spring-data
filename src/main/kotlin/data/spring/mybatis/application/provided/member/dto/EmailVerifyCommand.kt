package data.spring.mybatis.application.provided.member.dto

data class EmailVerifyCommand(
    val memberId: Long,
    val verificationCode: String
)
