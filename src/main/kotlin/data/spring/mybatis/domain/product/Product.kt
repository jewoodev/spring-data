package data.spring.mybatis.domain.product

import data.spring.mybatis.domain.clock
import data.spring.mybatis.domain.product.request.ProductUpdateCommand
import java.time.LocalDateTime

data class Product constructor(
    val productId: Long? = null,
    val productName: String?,
    val price: Int?,
    val quantity: Int?,
    val createdAt: LocalDateTime = LocalDateTime.now(clock()),
    var updatedAt: LocalDateTime = LocalDateTime.now(clock()),
) {
    companion object {
        fun updateAll(updateCommand: ProductUpdateCommand): Product {
            return Product(
                productId = updateCommand.productId,
                productName = updateCommand.productName,
                price = updateCommand.price,
                quantity = updateCommand.quantity,
                updatedAt = LocalDateTime.now(clock())
            )
        }
    }

    fun isUpdated(): Boolean {
        return this.productName != null || this.price != null || this.quantity != null
    }
}
