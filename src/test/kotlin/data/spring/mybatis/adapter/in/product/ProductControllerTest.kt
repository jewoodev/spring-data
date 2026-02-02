package data.spring.mybatis.adapter.`in`.product

import data.spring.mybatis.ControllerTestSupport
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class ProductControllerTest: ControllerTestSupport() {

    // ================================ Validation Test ================================
    @Test
    fun updateProductWithNoData() {
        // given
        val content = """
                {    
                    "updateRequests": [
                        {
                            "productId": 1,
                            "productName": null,
                            "price": null,
                            "quantity": null
                        }
                    ]
                }
                """.trimIndent()

        // when, then
        mockMvc.perform(
            MockMvcRequestBuilders.patch("/products/v1/update")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("수정할 정보가 없습니다."))
    }

    @Test
    fun updateProductWithShortName() {
        // given
        val content = """
                {    
                    "updateRequests": [
                        {
                            "productId": 1,
                            "productName": "s",
                            "price": null,
                            "quantity": null
                        }
                    ]
                }
                """.trimIndent()

        // when, then
        mockMvc.perform(
            MockMvcRequestBuilders.patch("/products/v1/update")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("상품명은 2자 이상 100자 이하로 입력해주세요."))
    }
}