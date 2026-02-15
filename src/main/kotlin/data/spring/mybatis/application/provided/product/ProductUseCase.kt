package data.spring.mybatis.application.provided.product

import data.spring.mybatis.application.provided.product.dto.ProductSearchCond
import data.spring.mybatis.application.provided.product.dto.ProductUpdateCommand
import data.spring.mybatis.domain.product.Product
import java.time.LocalDateTime

interface ProductUseCase {
    fun save(product: Product): Int
    fun saveAll(products: List<Product>): Int

    fun update(updateCommand: ProductUpdateCommand): Int
    fun updateAll(updateCommands: List<ProductUpdateCommand>): Int

    fun deleteAll(): Int

    fun findById(productId: Long): Product?
    fun findByCond(searchCond: ProductSearchCond? = null,
                   createdAt: LocalDateTime?,
                   productId: Long?,
                   size: Int = 21): List<Product>
    fun findAll(): List<Product>
}