package data.spring.mybatis.adapter.`in`.member.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class PasswordChangeRequest(
    @field:NotNull
    val memberId: Long,

    @field:NotNull
    @field:NotBlank
    val newPassword: String
)
