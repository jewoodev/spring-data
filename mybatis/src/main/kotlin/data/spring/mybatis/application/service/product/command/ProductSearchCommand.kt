package data.spring.mybatis.application.service.product.command

data class ProductSearchCommand(
    val productName: String?,
    val maxPrice: Int?
)