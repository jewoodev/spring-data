package data.spring.mybatis.domain.product.request

data class ProductUpdateCommand constructor(
    val productId: Long,
    val productName: String?,
    val price: Int?,
    val quantity: Int?
)
