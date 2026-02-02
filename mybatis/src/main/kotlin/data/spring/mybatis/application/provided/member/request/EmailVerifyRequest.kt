package data.spring.mybatis.application.provided.member.request

data class EmailVerifyRequest(
    val memberId: Long,
    val verificationCode: String
)