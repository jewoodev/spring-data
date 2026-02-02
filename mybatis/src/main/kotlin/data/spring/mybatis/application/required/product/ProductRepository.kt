package data.spring.mybatis.application.required.product

import data.spring.mybatis.application.service.product.command.ProductSearchCommand
import data.spring.mybatis.domain.product.Product

interface ProductRepository {
    fun save(product: Product)
    fun saveAll(products: List<Product>): Int

    fun findById(productId: Long): Product?
    fun findWithCond(searchCommand: ProductSearchCommand): List<Product>

    fun update(products: List<Product>): Int

    fun deleteAll(): Int
}