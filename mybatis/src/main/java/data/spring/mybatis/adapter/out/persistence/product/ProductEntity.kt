package data.spring.mybatis.adapter.out.persistence.product

import data.spring.mybatis.domain.product.Product

data class ProductEntity(
    val productId: Long? = null,
    val productName: String,
    val price: Int,
    val quantity: Int
) {
    companion object {
        fun fromDomain(product: Product): ProductEntity
            = ProductEntity(product.productId, product.productName, product.price, product.quantity)
    }

    fun toDomain(): Product
            = Product(this.productId, this.productName, this.price, this.quantity)
}
