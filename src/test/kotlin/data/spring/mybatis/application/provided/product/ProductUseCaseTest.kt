package data.spring.mybatis.application.provided.product

import data.spring.mybatis.IntegrationTestSupport
import data.spring.mybatis.application.exception.NoDataFoundException
import data.spring.mybatis.application.provided.product.dto.ProductSearchCond
import data.spring.mybatis.application.provided.product.dto.ProductUpdateCommand
import data.spring.mybatis.domain.product.Product
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

class ProductUseCaseTest: IntegrationTestSupport() {
    @AfterEach
    fun tearDown() {
        super.productUseCase.deleteAll()
    }

    @Test
    fun `save products and find all successfully`() {
        // given
        val sut = super.productUseCase
        val products = listOf(
            Product.create(productName = "상품1", price = 20000, quantity = 10),
            Product.create(productName = "상품2", price = 30000, quantity = 20),
            Product.create(productName = "상품3", price = 40000, quantity = 30)
        )

        // when
        val savedCnt = sut.saveAll(products)
        val searchCond = ProductSearchCond(null, null)
        val saved = sut.findByCond(searchCond = searchCond, createdAt = null, productId = null)

        // then
        assertThat(savedCnt).isEqualTo(3)
        assertThat(saved).extracting("productName").containsExactly("상품1", "상품2", "상품3")
        assertThat(saved).extracting("price").containsExactly(20000, 30000, 40000)
        assertThat(saved).extracting("quantity").containsExactly(10, 20, 30)
        for (i in 0..1) {
            assertThat(saved[i].createdAt).isBefore(saved[i + 1].createdAt)
            assertThat(saved[i].updatedAt).isBefore(saved[i + 1].updatedAt)
        }
    }

    @Test
    fun `find product by id successfully`() {
        val sut = super.productUseCase
        val products = listOf(
            Product.create(productName = "리얼 마이바티스", price = 30000, quantity = 100),
            Product.create(productName = "리얼 제이디비씨", price = 30000, quantity = 100))
        sut.saveAll(products)

        val saved = sut.findById(1L)

        assertThat(saved!!.productName).isEqualTo(products[0].productName)
        assertThat(saved.price).isEqualTo(products[0].price)
        assertThat(saved.quantity).isEqualTo(products[0].quantity)
    }

    @Test
    fun `find products by cursor successfully`() {
        val sut = super.productUseCase
        val products = mutableListOf(Product.create("testProduct0", 10000, 100))
        for (i in 1..30) {
            products.add(Product.create("testProduct$i", 10000, 100))
        }
        sut.saveAll(products)

        val found = sut.findByCond(createdAt = null, productId = null)

        assertThat(found).hasSize(21)
        assertThat(found[0].productName).isEqualTo(products[0].productName)
        assertThat(found[20].productName).isEqualTo(products[20].productName)
    }

    @Test
    fun `find products by cursor with conditions successfully`() {
        // given
        val sut = super.productUseCase
        val products = listOf(
            Product.create(productName = "리얼 마이바티스", price = 30000, quantity = 100),
            Product.create(productName = "리얼 제이디비씨", price = 30000, quantity = 100),
            Product.create(productName = "리얼 비쌈", price = 50000, quantity = 100)
        )
        sut.saveAll(products)

        // when
        val searchCommand = ProductSearchCond(productName = null, maxPrice = 30000)
        val saved = sut.findByCond(searchCond = searchCommand, createdAt = null, productId = null)

        // then
        assertThat(saved).hasSize(2)
        assertThat(saved).extracting("productName").contains("리얼 마이바티스", "리얼 제이디비씨")
    }

    @Test
    fun `find product by id fails when no matching product exists`() {
        // given
        val sut = super.productUseCase
        sut.save(Product.create(productName = "testProduct", price = 10000, quantity = 100))

        // when & then
        assertThatThrownBy { sut.findById(2L) }
                .isInstanceOf(NoDataFoundException::class.java);
    }

    @Test
    fun `find products by conditions fails when no matching product exists`() {
        // given
        val sut = super.productUseCase
        val products = listOf(
            Product.create(productName = "리얼 마이바티스", price = 30000, quantity = 100),
            Product.create(productName = "리얼 제이디비씨", price = 30000, quantity = 100),
            Product.create(productName = "리얼 비쌈", price = 50000, quantity = 100)
        )
        sut.saveAll(products)

        // when & then
        val searchCommand = ProductSearchCond(productName = null, maxPrice = 20000)
        assertThatThrownBy { sut.findByCond(searchCond = searchCommand, createdAt = null, productId = null) }
            .isInstanceOf(NoDataFoundException::class.java)
    }

    @Test
    fun `update products successfully`() {
        // given
        val sut = super.productUseCase
        val products = listOf(
            Product.create(productName = "상품1", price = 20000, quantity = 10),
            Product.create(productName = "상품2", price = 30000, quantity = 20),
            Product.create(productName = "상품3", price = 40000, quantity = 30)
        )
        products.forEach { sut.save(it) }
        val updateCommands = listOf(
            ProductUpdateCommand(1L, "상품4", null, null),
            ProductUpdateCommand(2L, "상품5", null, null),
            ProductUpdateCommand(3L, "상품6", null, null)
        )

        // when
        val updateCnt = sut.updateAll(updateCommands)

        // then
        assertThat(updateCnt).isEqualTo(3)
        val saved = sut.findAll()
        assertThat(saved).extracting("productName").containsExactly("상품4", "상품5", "상품6")
    }
}