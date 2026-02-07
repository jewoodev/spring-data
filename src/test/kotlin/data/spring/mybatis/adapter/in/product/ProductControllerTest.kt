package data.spring.mybatis.adapter.`in`.product

import data.spring.mybatis.ControllerTestSupport
import data.spring.mybatis.adapter.`in`.product.request.CursorInfo
import data.spring.mybatis.application.service.product.command.ProductSearchCond
import data.spring.mybatis.domain.testClock
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime

class ProductControllerTest: ControllerTestSupport() {
    @Test
    fun getProductsWithPagingSuccessfully() {
        val url = "/products/v1/list"
        val products = ProductFixture.createProducts(number = 20)
        every { productUseCase.findByCond(createdAt = null, productId = null) } returns products

        mockMvc.perform(get(url))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content.size()").value(20))
            .andExpect(jsonPath("$.hasNext").value("true"))
            .andExpect(jsonPath("$.content[19].productName").value(products[19].productName.value))
            .andExpect { jsonPath("$.content[20].price").value(products[20].price.amount) }
            .andExpect { jsonPath("$.content[20].quantity").value(products[20].quantity.value) }
    }

    @Test
    fun getProductsWithCondSuccessfully() {
        val url = "/products/v1/list"
        val queryParam = "?maxPrice=10000"
        val products = ProductFixture.createProducts(number = 21)
        val searchCond = ProductSearchCond(productName = null, maxPrice = 10000)
        every { productUseCase.findByCond(searchCond = searchCond, createdAt = null, productId = null) } returns products.subList(0, 10)

        mockMvc.perform(get(url + queryParam))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content.size()").value(10))
            .andExpect(jsonPath("$.hasNext").value("false"))
            .andExpect(jsonPath("$.content[9].productName").value(products[9].productName.value))
            .andExpect(jsonPath("$.content[7].price").value(products[7].price.amount))
            .andExpect(jsonPath("$.content[1].quantity").value(products[1].quantity.value))
    }

    @Test
    fun getProductsWithCursorSuccessfully() {
        val url = "/products/v1/list"
        val createdAt = LocalDateTime.now(testClock())
        val cursor = CursorInfo(createdAt = createdAt, id = 21L).encode()
        val queryParam = "?cursor=$cursor"
        val products = ProductFixture.createProducts(startIdx = 21, number = 20)
        every { productUseCase.findByCond(createdAt = createdAt, productId = 21L) } returns products

        mockMvc.perform(get(url + queryParam))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content.size()").value(20))
            .andExpect(jsonPath("$.hasNext").value("true"))
            .andExpect(jsonPath("$.content[19].productName").value(products[19].productName.value))
            .andExpect { jsonPath("$.content[4].price").value(products[4].price.amount) }
            .andExpect { jsonPath("$.content[2].quantity").value(products[2].quantity.value) }
    }

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