package data.spring.mybatis.adapter.`in`.product

import data.spring.mybatis.ControllerTestSupport
import data.spring.mybatis.adapter.`in`.product.request.CursorInfo
import data.spring.mybatis.application.provided.product.dto.ProductCreateCommand
import data.spring.mybatis.application.provided.product.dto.ProductSearchCond
import data.spring.mybatis.domain.testClock
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

class ProductControllerTest: ControllerTestSupport() {
    // ================================ Paging Test ================================
    @Test
    fun `get products with paging successfully`() {
        val url = "/products/v1/list"
        val products = ProductFixture.createProducts(number = 20)
        every { productUseCase.findByCond(createdAt = null, productId = null) } returns products

        mockMvc.perform(get(url))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content.size()").value(20))
            .andExpect(jsonPath("$.hasNext").value("true"))
            .andExpect(jsonPath("$.content[19].productName").value(products[19].productName.value))
            .andExpect(jsonPath("$.content[19].price").value(products[19].price.amount))
            .andExpect(jsonPath("$.content[19].quantity").value(products[19].quantity.amount))
    }

    @Test
    fun `get products with conditions successfully`() {
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
            .andExpect(jsonPath("$.content[1].quantity").value(products[1].quantity.amount))
    }

    @Test
    fun `get products with cursor successfully`() {
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
            .andExpect(jsonPath("$.content[4].price").value(products[4].price.amount))
            .andExpect(jsonPath("$.content[2].quantity").value(products[2].quantity.amount))
    }

    // ================================ Validation Test ================================
    @Test
    fun `create product successfully`() {
        // given
        val url = "/products/v1/save"
        val content = """
                {
                    "productName": "test product",
                    "price": 1000,
                    "quantity": 10
                }
            """.trimIndent()

        val createCommand = ProductCreateCommand("test product", 1000, 10)
        every { productUseCase.save(createCommand) } returns 1

        // when, then
        mockMvc.perform(
            MockMvcRequestBuilders.post(url)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$").value("상품 저장에 성공했습니다."))
    }

    @Test
    fun `create product fails when product name is too short`() {
        // given
        val url = "/products/v1/save"
        val content = """
                {
                    "productName": "t",
                    "price": 1000,
                    "quantity": 10
                }
            """.trimIndent()

        // when, then
        mockMvc.perform(
            MockMvcRequestBuilders.post(url)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message").value("상품명은 2자 이상 100자 이하여야 합니다."))
    }

    @Test
    fun `create product fails when price is less than 1`() {
        // given
        val url = "/products/v1/save"
        val content = """
                {
                    "productName": "test product",
                    "price": 0,
                    "quantity": 10
                }
            """.trimIndent()

        // when, then
        mockMvc.perform(
            MockMvcRequestBuilders.post(url)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message").value("가격은 1원 이상이어야 합니다."))
    }

    @Test
    fun `update product fails when no data is provided`() {
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
    fun `update product fails when product name is too short`() {
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