package data.spring.mybatis.application.provided.product

import data.spring.mybatis.application.service.product.command.ProductSearchCommand
import data.spring.mybatis.domain.product.Product
import data.spring.mybatis.domain.product.request.ProductUpdateCommand

interface ProductUseCase {
    fun save(product: Product)
    fun saveAll(products: List<Product>): Int

    fun findById(productId: Long): Product?
    fun findWithCond(searchCommand: ProductSearchCommand): List<Product>

    fun update(updateCommands: List<ProductUpdateCommand>): Int

    fun deleteAll(): Int
}