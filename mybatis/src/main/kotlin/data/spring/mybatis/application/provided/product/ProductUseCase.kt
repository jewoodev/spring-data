package data.spring.mybatis.application.provided.product

import data.spring.mybatis.application.service.product.command.ProductSearchCommand
import data.spring.mybatis.domain.product.Product

interface ProductUseCase : ProductUseCaseEx {
    fun findById(productId: Long): Product?

    fun findWithCond(searchCommand: ProductSearchCommand): List<Product>

    fun save(product: Product)

    fun saveAll(products: List<Product>): Int

    fun deleteAll(): Int
}