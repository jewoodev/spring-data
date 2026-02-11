package data.spring.mybatis.adapter.`in`.member.request

import data.spring.mybatis.application.provided.member.dto.EmailVerifyCommand

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class EmailVerifyRequest(
    @field:NotNull
    val memberId: Long,

    @field:NotNull
    @field:NotBlank
    val verificationCode: String
) {
    fun toCommand(): EmailVerifyCommand = EmailVerifyCommand(memberId, verificationCode)
}