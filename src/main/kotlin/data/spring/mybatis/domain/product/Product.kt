package data.spring.mybatis.domain.product

import data.spring.mybatis.domain.clock
import data.spring.mybatis.application.service.product.command.ProductUpdateCommand
import java.time.LocalDateTime

data class Product(
    val productId: Long? = null,
    val productName: ProductName,
    val price: Price,
    val quantity: Quantity,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
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
        newName: ProductName? = null,
        newPrice: Price? = null
    ): Product {
        require(newName != null || newPrice != null) { "상품 수정의 필수 조건이 만족되지 않았습니다." }
        return copy(
            productName = newName ?: productName,
            price = newPrice ?: price,
            updatedAt = LocalDateTime.now(clock())
        )
    }

    companion object {
        fun create(
            productName: ProductName,
            price: Price,
            quantity: Quantity
        ): Product {
            val now = LocalDateTime.now(clock())
            return Product(
                productId = null,
                productName = productName,
                price = price,
                quantity = quantity,
                createdAt = now,
                updatedAt = now
            )
        }
    }
}
