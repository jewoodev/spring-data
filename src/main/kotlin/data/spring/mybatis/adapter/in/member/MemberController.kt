package data.spring.mybatis.adapter.`in`.member

import data.spring.mybatis.application.provided.member.MemberUseCase
import data.spring.mybatis.adapter.`in`.member.request.EmailVerifyRequest
import data.spring.mybatis.adapter.`in`.member.request.MemberCreateRequest
import data.spring.mybatis.adapter.`in`.member.request.MemberLeaveRequest
import data.spring.mybatis.adapter.`in`.member.request.PasswordChangeRequest
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
        memberUseCase.register(createRequest.toCommand())
        return ResponseEntity.created(URI.create(createRequest.username)).body("회원가입에 성공했습니다!")
    }

    @PostMapping("/send-vfc")
    fun sendVerificationCode(@Valid @RequestBody codeRequest: VfcCodeSendRequest): ResponseEntity<String> {
        memberUseCase.sendVerificationCode(codeRequest.toCommand())
        return ResponseEntity.ok().body("이메일 인증번호가 전송되었습니다!")
    }

    @PostMapping("/verify")
    fun verify(@Valid @RequestBody verifyRequest: EmailVerifyRequest): ResponseEntity<String> {
        memberUseCase.verify(verifyRequest.toCommand())
        return ResponseEntity.ok().body("이메일 인증이 완료되었습니다!")
    }

    @PatchMapping("/change-password")
    fun changePassword(@Valid @RequestBody request: PasswordChangeRequest): ResponseEntity<String> {
        memberUseCase.changePassword(request.memberId, request.newPassword)
        return ResponseEntity.ok().body("비밀번호 변경이 완료되었습니다!")
    }

    @PatchMapping("/leave")
    fun leave(@Valid @RequestBody request: MemberLeaveRequest): ResponseEntity<String> {
        memberUseCase.leave(request.memberId)
        return ResponseEntity.ok().body("회원 탈퇴가 완료되었습니다!")
    }
}