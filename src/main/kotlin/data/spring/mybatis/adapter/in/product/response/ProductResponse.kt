package data.spring.mybatis.adapter.`in`.product.response

import data.spring.mybatis.domain.product.Product
import java.time.LocalDateTime

data class ProductResponse(
    val productName: String,
    val price: Int,
    val quantity: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun fromDomain(product: Product): ProductResponse {
            return ProductResponse(
                productName = product.productName.value,
                price = product.price.amount,
                quantity = product.quantity.value,
                createdAt = product.createdAt,
                updatedAt = product.updatedAt
            )
        }
    }
}
