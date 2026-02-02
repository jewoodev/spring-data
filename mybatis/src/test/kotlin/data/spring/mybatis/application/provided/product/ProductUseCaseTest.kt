package data.spring.mybatis.application.provided.product

import data.spring.mybatis.IntegrationTestSupport
import data.spring.mybatis.application.exception.NoDataFoundException
import data.spring.mybatis.application.service.product.command.ProductSearchCommand
import data.spring.mybatis.application.service.product.command.ProductUpdateCommand
import data.spring.mybatis.domain.product.Product
import data.spring.mybatis.domain.testClock
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ProductUseCaseTest: IntegrationTestSupport() {
    val now = LocalDateTime.now(testClock())

    @AfterEach
    fun tearDown() {
        super.productUseCase.deleteAll()
    }

    @Test
    fun saveProductsSuccessfully() {
        val sut = super.productUseCase
        val products = listOf(
            Product(productName = "상품1", price = 20000, quantity = 10, createdAt = now, updatedAt = now),
            Product(productName = "상품2", price = 30000, quantity = 20, createdAt = now, updatedAt = now),
            Product(productName = "상품3", price = 40000, quantity = 30, createdAt = now, updatedAt = now)
        )
        sut.saveAll(products)
        val expected = listOf(
            Product(1L, "상품1", 20000, 10, createdAt = now, updatedAt = now),
            Product(2L, "상품2", 30000, 20, createdAt = now, updatedAt = now),
            Product(3L, "상품3", 40000, 30, createdAt = now, updatedAt = now)
        )

        val savedProducts = sut.findWithCond(ProductSearchCommand(null, null))

        assertThat(savedProducts).isEqualTo(expected)
    }

    @Test
    fun findProductSuccessfully() {
        val sut = super.productUseCase
        val product = Product(productName = "리얼 마이바티스", price = 30000, quantity = 100, createdAt = now, updatedAt = now)
        sut.save(product)
        val expected = Product(productId = 1L, productName = "리얼 마이바티스", price = 30000, quantity = 100,
            createdAt = now, updatedAt = now)

        val savedProduct = sut.findById(1L)

        assertThat(savedProduct).isEqualTo(expected)
    }

    @Test
    fun findProductsSuccessfully() {
        val sut = super.productUseCase
        val products = listOf(
            Product(productName = "리얼 마이바티스", price = 30000, quantity = 100),
            Product(productName = "리얼 제이디비씨", price = 30000, quantity = 100),
            Product(productName = "리얼 비쌈", price = 50000, quantity = 100)
        )
        sut.saveAll(products)

        val savedProducts = sut.findWithCond(ProductSearchCommand(productName = null, maxPrice = 30000))
        assertThat(savedProducts).hasSize(2)
        assertThat(savedProducts).extracting("productName").contains("리얼 마이바티스", "리얼 제이디비씨")
    }

    @Test
    fun updateProductsSuccessfully() {
        // given
        val sut = super.productUseCase
        val products = listOf(
            Product(productName = "상품1", price = 20000, quantity = 10),
            Product(productName = "상품2", price = 30000, quantity = 20),
            Product(productName = "상품3", price = 40000, quantity = 30)
        )
        sut.saveAll(products)
        val updateCommands = listOf(
            ProductUpdateCommand(1L, "상품4", null, null),
            ProductUpdateCommand(2L, "상품5", null, null),
            ProductUpdateCommand(3L, "상품6", null, null)
        )

        // when
        sut.updateList(updateCommands)

        // then
        val savedProducts = sut.findWithCond(ProductSearchCommand(productName = null, maxPrice = null))
        assertThat(savedProducts).extracting("productName").containsExactly("상품4", "상품5", "상품6")
    }

    @Test
    fun findWhenNoProduct() {
        val sut = super.productUseCase

        assertThatThrownBy { sut.findById(1L) }
                .isInstanceOf(NoDataFoundException::class.java);
    }

    @Test
    fun findAllWhenNoProduct() {
        val sut = super.productUseCase

        assertThatThrownBy { sut.findWithCond(ProductSearchCommand(productName = null, maxPrice = null)) }
            .isInstanceOf(NoDataFoundException::class.java)
    }
}