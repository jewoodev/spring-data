package data.spring.mybatis.adapter.`in`.member

import data.spring.mybatis.application.provided.member.MemberUseCase
import data.spring.mybatis.application.provided.member.request.EmailVerifyRequest
import data.spring.mybatis.adapter.`in`.member.request.MemberCreateRequest
import data.spring.mybatis.adapter.`in`.member.request.VfcCodeSendRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RequestMapping("/members/v1")
@RestController
class MemberController(
    val memberUseCase: MemberUseCase
) {
    @PostMapping("/sign-up")
    fun signUp(@Valid @RequestBody createRequest: MemberCreateRequest): ResponseEntity<String> {
        return memberUseCase.register(createRequest).let {
            ResponseEntity.created(URI.create(createRequest.username)).body("회원가입에 성공했습니다!")
        }
    }

    @PostMapping("/send-vfc")
    fun sendVerificationCode(@RequestBody codeRequest: VfcCodeSendRequest): ResponseEntity<String> {
        memberUseCase.sendVerificationCode(codeRequest)
        return ResponseEntity.ok().body("이메일 인증번호가 전송되었습니다!")
    }

    @PostMapping("/verify")
    fun verify(@RequestBody verifyRequest: EmailVerifyRequest) {
        memberUseCase.verify(verifyRequest)
    }

    @PatchMapping("/change-password")
    fun changePassword() {

    }
}