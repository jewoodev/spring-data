package data.spring.mybatis.application.provided.product.dto

data class ProductUpdateCommand(
    val productId: Long,
    val productName: String?,
    val price: Int?,
    val quantity: Int?
)
