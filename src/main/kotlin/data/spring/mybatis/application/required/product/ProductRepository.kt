package data.spring.mybatis.application.required.product

import data.spring.mybatis.application.service.product.command.ProductSearchCond
import data.spring.mybatis.domain.product.Product
import java.time.LocalDateTime

interface ProductRepository {
    fun save(product: Product): Int

    fun truncate(): Int

    fun findById(productId: Long): Product?
    fun findByCond(searchCond: ProductSearchCond?,
                   createdAt: LocalDateTime?,
                   productId: Long?,
                   size: Int): List<Product>
    fun findAll(): List<Product>
}