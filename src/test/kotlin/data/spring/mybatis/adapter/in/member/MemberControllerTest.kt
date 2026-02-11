package data.spring.mybatis.adapter.`in`.member

import data.spring.mybatis.ControllerTestSupport
import data.spring.mybatis.domain.email.VerificationEmailContent
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class MemberControllerTest : ControllerTestSupport() {
    @Test
    fun `sign up member successfully`() {
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
    fun `sign up member fails due to short username`() {
        val content = """
            {
                "username": "u",
                "password": "password123",
                "email": "test@example.com"
            }
        """.trimIndent()

        mockMvc.perform(post("/members/v1/sign-up")
            .contentType(APPLICATION_JSON)
            .content(content)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message").exists())
    }

    @Test
    fun `sign up member fails due to short password`() {
        val content = """
            {
                "username": "testuser",
                "password": "pass",
                "email": "test@example.com"
            }
        """.trimIndent()

        mockMvc.perform(post("/members/v1/sign-up")
            .contentType(APPLICATION_JSON)
            .content(content)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message").exists())
    }

    @ParameterizedTest
    @ValueSource(strings = ["plainaddress", "#@%^%#$@#$@#.com", "@example.com", "Joe Smith <email@example.com>", "email.example.com"])
    fun `sign up member fails due to invalid email`(invalidEmail: String) {
        val content = """
            {
                "username": "testuser",
                "password": "testpassword",
                "email": $invalidEmail
            }
        """.trimIndent()

        mockMvc.perform(post("/members/v1/sign-up")
            .contentType(APPLICATION_JSON)
            .content(content)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message").exists())
    }

    @Test
    fun `send member's verification code successfully via email`() {
        mockMvc.perform(post("/members/v1/send-vfc")
            .contentType(APPLICATION_JSON)
            .content("""{"memberId": 1}""")
        )
            .andExpect(status().isOk)
            .andExpect(content().string("이메일 인증번호가 전송되었습니다!"))
    }

    @ParameterizedTest
    @ValueSource(strings = ["{}", "{memberId: null}"])
    fun `send member's verification code fails due to missing memberId`(invalidContent: String) {
        mockMvc.perform(post("/members/v1/send-vfc")
            .contentType(APPLICATION_JSON)
            .content(invalidContent)
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `verify member's email successfully`() {
        val content = """
            {
                "memberId": 1,
                "verificationCode": "123456"
            }
        """.trimIndent()

        mockMvc.perform(post("/members/v1/verify")
            .contentType(APPLICATION_JSON)
            .content(content)
        )
            .andExpect(status().isOk)
            .andExpect(content().string("이메일 인증이 완료되었습니다!"))
    }

    @ParameterizedTest
    @ValueSource(strings = ["{memberId: 1, verificationCode: null}","{memberId: 1, verificationCode: \"\"}"])
    fun `verify member's email fails due to missing verification code`(invalidContent: String) {

        mockMvc.perform(post("/members/v1/verify")
            .contentType(APPLICATION_JSON)
            .content(invalidContent)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message").exists())
    }

    @Test
    fun `change member's password successfully`() {
        val content = """
            {
                "memberId": 1,
                "newPassword": "newpassword123"
            }
        """.trimIndent()

        mockMvc.perform(patch("/members/v1/change-password")
            .contentType(APPLICATION_JSON)
            .content(content)
        )
            .andExpect(status().isOk)
            .andExpect(content().string("비밀번호 변경이 완료되었습니다!"))
    }

    @ParameterizedTest
    @ValueSource(strings = ["{memberId: 1, newPassword: null}","{memberId: 1, newPassword: null}"])
    fun `change member's password fails due to missing password`(invalidContent: String) {

        mockMvc.perform(patch("/members/v1/change-password")
            .contentType(APPLICATION_JSON)
            .content(invalidContent)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message").exists())
    }

    @ParameterizedTest
    @ValueSource(strings = ["{newPassword: \"password123\"}", "{memberId: null, newPassword: \"password123\"}"])
    fun `change member's password fails due to missing memberId`(invalidContent: String) {
        mockMvc.perform(patch("/members/v1/change-password")
            .contentType(APPLICATION_JSON)
            .content(invalidContent)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message").exists())
    }

    @Test
    fun `leave member successfully`() {
        val content = """
            {
                "memberId": 1
            }
        """.trimIndent()

        mockMvc.perform(patch("/members/v1/leave")
            .contentType(APPLICATION_JSON)
            .content(content)
        )
            .andExpect(status().isOk)
            .andExpect(content().string("회원 탈퇴가 완료되었습니다!"))
    }

    @ParameterizedTest
    @ValueSource(strings = ["{}", "{memberId: null}"])
    fun `leave member fails due to missing memberId`(invalidContent: String) {

        mockMvc.perform(patch("/members/v1/leave")
            .contentType(APPLICATION_JSON)
            .content(invalidContent)
        )
            .andExpect(status().isBadRequest)
    }
}