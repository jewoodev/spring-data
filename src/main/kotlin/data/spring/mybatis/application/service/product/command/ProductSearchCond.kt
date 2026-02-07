package data.spring.mybatis.application.service.product.command

data class ProductSearchCond(
    val productName: String?,
    val maxPrice: Int?
)