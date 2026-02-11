package data.spring.mybatis.domain.product

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class ProductTest {
    @Test
    fun createSuccessfully() {
        val product = Product.create("Test Product", 1000, 100)
        assertThat(product)
            .extracting("productName", "price", "quantity")
            .containsExactly("Test Product", 1000, 100)
    }
    
    @Test
    fun increaseQuantityuccessfully() {
        // given
        val original = Product.create("Test Product", 1000, 100)
        
        // when
        val increased = original.increaseQuantity(50)
        
        // then
        assertThat(increased.quantity.value).isEqualTo(150)
        assertThat(increased.updatedAt).isAfterOrEqualTo(original.updatedAt)
    }

    @Test
    fun decreasedQuantitySuccessfully() {
        // given
        val product = Product.create("Test Product", 1000, 100)
        
        // when
        val decreasedProduct = product.decreaseQuantity(50)
        
        // then
        assertThat(decreasedProduct.quantity.value).isEqualTo(50)
        assertThat(decreasedProduct.updatedAt).isAfterOrEqualTo(product.updatedAt)
    }

    @Test
    fun infoUpdateSuccessfully() {
        // given
        val product = Product.create("Test Product", 1000, 100)
        val newName = "Updated Product"
        val newPrice = 2000
        
        // when
        val updatedProduct = product.updateInfo(newName, newPrice)
        
        // then
        assertThat(updatedProduct.productName.value).isEqualTo(newName)
        assertThat(updatedProduct.price.amount).isEqualTo(newPrice)
        assertThat(updatedProduct.updatedAt).isAfterOrEqualTo(product.updatedAt)
    }

    @Test
    fun infoCanBeUpdatedWithNameOnly() {
        // given
        val product = Product.create("Test Product", 1000, 100)
        val newName = "Updated Product"
        
        // when
        val updatedProduct = product.updateInfo(newName = newName)
        
        // then
        assertThat(updatedProduct.productName.value).isEqualTo(newName)
        assertThat(updatedProduct.price).isEqualTo(product.price)
    }

    @Test
    fun infoCanBeUpdatedWithPriceOnly() {
        // given
        val original = Product.create("Test Product", 1000, 100)
        val newPrice = 2000
        
        // when
        val updated = original.updateInfo(newPrice = newPrice)
        
        // then
        assertThat(updated.productName).isEqualTo(original.productName)
        assertThat(updated.price.amount).isEqualTo(newPrice)
    }

    @Test
    fun infoUpdateInFailureWhenNoInfoProvided() {
        // given
        val product = Product.create("Test Product", 1000, 100)
        
        // when & then
        assertThatThrownBy { product.updateInfo(null, null) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("상품 수정의 필수 조건이 만족되지 않았습니다.")
    }
}