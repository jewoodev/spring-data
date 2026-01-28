package data.spring.mybatis.domain.product

data class Product(
    val productId: Long?= null,
    val productName: String,
    val price: Int,
    val quantity: Int
) {
}
