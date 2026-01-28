package data.spring.mybatis.application.service.product.command

data class ProductUpdateCommand(
    val productId: Long,
    val productName: String?,
    val price: Int?,
    val quantity: Int?
)
