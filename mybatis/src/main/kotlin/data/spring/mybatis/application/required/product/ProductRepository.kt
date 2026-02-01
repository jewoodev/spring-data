package data.spring.mybatis.application.required.product

import data.spring.mybatis.application.service.product.command.ProductSearchCommand
import data.spring.mybatis.application.service.product.command.ProductUpdateCommand
import data.spring.mybatis.domain.product.Product
import java.util.*

interface ProductRepository : ProductRepositoryEx {
    fun save(product: Product)

    fun saveAll(products: List<Product>): Int

    fun findById(productId: Long): Product?

    fun findWithCond(searchCommand: ProductSearchCommand): List<Product>

    fun deleteAll(): Int
}