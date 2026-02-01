package data.spring.mybatis.adapter.`in`.member

import data.spring.mybatis.ControllerTestSupport
import data.spring.mybatis.domain.email.createVerificationCode
import data.spring.mybatis.domain.email.createVerificationEmail
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class MemberControllerTest : ControllerTestSupport() {
    @Test
    fun signUp() {
        val content = """
            {
                "username": "testuser",
                "password": "testpassword",
                "email": "test@example.com"
            }
        """.trimIndent()

        mockMvc.perform(post("/members/v1/sign-up")
            .contentType(APPLICATION_JSON)
            .content(content)
        )
            .andExpect(status().isCreated)
            .andExpect(content().string("회원가입에 성공했습니다!"))
    }

    @Test
    fun sendVerificationCode() {
        val expectedEmailContent = createVerificationEmail(createVerificationCode()) // will send this bia email

        mockMvc.perform(post("/members/v1/send-vfc")
            .contentType(APPLICATION_JSON)
            .content("""{"memberId": 1}""")
        )
            .andExpect(status().isOk)
            .andExpect(content().string("이메일 인증번호가 전송되었습니다!"))
    }
}