package data.spring.mybatis.application.provided.product

import data.spring.mybatis.application.service.product.command.ProductSearchCommand
import data.spring.mybatis.application.service.product.command.ProductUpdateCommand
import data.spring.mybatis.domain.product.Product
import java.util.*

interface ProductUseCase {
    fun findById(productId: Long): Optional<Product>

    fun findAll(searchCommand: ProductSearchCommand): List<Product>

    fun save(product: Product)

    fun saveAll(products: List<Product>): Int

    fun update(updateCommand: ProductUpdateCommand)

    fun updateAll(updateCommands: List<ProductUpdateCommand>): Int
}