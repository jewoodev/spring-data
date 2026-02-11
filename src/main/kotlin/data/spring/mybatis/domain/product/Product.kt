package data.spring.mybatis.domain.product

import data.spring.mybatis.domain.clock
import data.spring.mybatis.application.service.product.command.ProductUpdateCommand
import java.time.LocalDateTime

data class Product(
    val productId: Long? = null,
    val productName: ProductName,
    val price: Price,
    val quantity: Quantity,
    val createdAt: LocalDateTime = LocalDateTime.now(clock()),
    val updatedAt: LocalDateTime = LocalDateTime.now(clock())
) {
    fun increaseQuantity(amount: Int): Product {
        return copy(
            quantity = quantity.increase(amount),
            updatedAt = LocalDateTime.now(clock())
        )
    }

    fun decreaseQuantity(amount: Int): Product {
        return copy(
            quantity = quantity.decrease(amount),
            updatedAt = LocalDateTime.now(clock())
        )
    }

    fun updateInfo(
        newName: String? = null,
        newPrice: Int? = null
    ): Product {
        require(newName != null || newPrice != null) { "상품 수정의 필수 조건이 만족되지 않았습니다." }
        return copy(
            productName = if (newName != null) ProductName(newName) else productName,
            price = if (newPrice != null) Price(newPrice) else price,
            updatedAt = LocalDateTime.now(clock())
        )
    }

    companion object {
        fun create(
            productName: String,
            price: Int,
            quantity: Int
        ): Product {
            return Product(
                productId = null,
                productName = ProductName(productName),
                price = Price(price),
                quantity = Quantity(quantity)
            )
        }
    }
}
