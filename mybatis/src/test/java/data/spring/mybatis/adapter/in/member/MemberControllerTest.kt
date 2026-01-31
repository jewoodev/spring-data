package data.spring.mybatis.adapter.`in`.member

import data.spring.mybatis.ControllerTestSupport
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

}