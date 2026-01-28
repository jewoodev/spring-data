package data.spring.mybatis.application.provided.product

import data.spring.mybatis.IntegrationTestSupport
import data.spring.mybatis.adapter.out.persistence.product.ProductEntity
import data.spring.mybatis.application.service.product.command.ProductSearchCommand
import data.spring.mybatis.application.service.product.command.ProductUpdateCommand
import data.spring.mybatis.domain.product.Product
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

class ProductUseCaseTest: IntegrationTestSupport() {
    @AfterEach
    fun tearDown() {
        super.productRepository.deleteAll()
    }

    @Test
    fun saveProductsSuccessfully() {
        val products = listOf(
            Product(productName = "상품1", price = 20000, quantity = 10),
            Product(productName = "상품2", price = 30000, quantity = 20),
            Product(productName = "상품3", price = 40000, quantity = 30)
        )

        val sut = super.productUseCase
        sut.saveAll(products)
        val expected = listOf(
            Product(1L, "상품1", 20000, 10),
            Product(2L, "상품2", 30000, 20),
            Product(3L, "상품3", 40000, 30)
        )

        val savedProducts = sut.findAll(ProductSearchCommand(null, null))

        Assertions.assertThat(savedProducts).isEqualTo(expected)
    }

    @Test
    fun findProductSuccessfully() {
        val product = Product(productName = "리얼 마이바티스", price = 30000, quantity = 100)
        println(product.productName)
        val sut: ProductUseCase = super.productUseCase
        sut.save(product)
        val expected = Product(1L, "리얼 마이바티스", 30000, 100)

        val savedProduct = sut.findById(1L).get()

        Assertions.assertThat(savedProduct).isEqualTo(expected)
    }

    @Test
    fun findProductsSuccessfully() {
        val products = listOf(
            Product(productName = "리얼 마이바티스", price = 30000, quantity = 100),
            Product(productName = "리얼 제이디비씨", price = 30000, quantity = 100),
            Product(productName = "리얼 비쌈", price = 50000, quantity = 100)
        )
        val sut = super.productUseCase
        sut.saveAll(products)

        val savedProducts = sut.findAll(ProductSearchCommand(productName = null, maxPrice = 30000))
        Assertions.assertThat(savedProducts).hasSize(2)
        Assertions.assertThat(savedProducts).extracting("productName").contains("리얼 마이바티스", "리얼 제이디비씨")
    }

    @Test
    fun updateProductsSuccessfully() {
        // given
        val products = listOf(
            Product(productName = "상품1", price = 20000, quantity = 10),
            Product(productName = "상품2", price = 30000, quantity = 20),
            Product(productName = "상품3", price = 40000, quantity = 30)
        )
        super.productRepository.saveAll(products)

        val updateCommands = listOf(
            ProductUpdateCommand(1L, "상품4", null, null),
            ProductUpdateCommand(2L, "상품5", null, null),
            ProductUpdateCommand(3L, "상품6", null, null)
        )
        val sut = super.productUseCase

        // when
        sut.updateAll(updateCommands)

        // then
        val savedProducts = super.productRepository.findAll(ProductSearchCommand(productName = null, maxPrice = null))
        Assertions.assertThat(savedProducts).extracting("productName").containsExactly("상품4", "상품5", "상품6")
    }
}