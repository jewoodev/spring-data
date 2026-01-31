package data.spring.mybatis.adapter.`in`.member

import data.spring.mybatis.adapter.`in`.member.request.MemberCreateRequest
import data.spring.mybatis.application.provided.member.MemberUseCase
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RequestMapping("/members/v1")
@RestController
class MemberController(
    val memberUseCase: MemberUseCase
) {
    @PostMapping("/sign-up")
    fun signUp(@Valid @RequestBody createRequest: MemberCreateRequest): ResponseEntity<String> {
        return createRequest.toDomain().let {
            memberUseCase.register(it)
            ResponseEntity.created(URI.create(it.username)).body("회원가입에 성공했습니다!")
        }
    }
}