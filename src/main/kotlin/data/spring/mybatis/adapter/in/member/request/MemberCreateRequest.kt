package data.spring.mybatis.adapter.`in`.member.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class MemberCreateRequest(
    @field:NotNull
    @field:Size(min = 2, max = 100, message = "아이디는 5자 이상 100자 이하로 입력해주세요.")
    val username: String,

    @field:NotNull
    @field:Size(min = 8, max = 100, message = "비밀번호는 8자 이상 100자 이하로 입력해주세요.")
    val password: String,

    @field:Email
    val email: String
)