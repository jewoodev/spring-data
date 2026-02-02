package data.spring.mybatis.application.service.product

import data.spring.mybatis.application.exception.NoDataFoundException
import data.spring.mybatis.application.provided.product.ProductUseCase
import data.spring.mybatis.application.required.product.ProductRepository
import data.spring.mybatis.application.service.product.command.ProductSearchCommand
import data.spring.mybatis.domain.product.Product
import data.spring.mybatis.domain.product.request.ProductUpdateCommand
import org.springframework.transaction.annotation.Transactional

open class ProductService(
    val productRepository: ProductRepository
): ProductUseCase {
    override fun findById(productId: Long): Product? {
        return this.productRepository.findById(productId) ?: throw NoDataFoundException("Product not found with id: ${productId}.")
    }

    override fun findWithCond(searchCommand: ProductSearchCommand): List<Product> {
        return this.productRepository.findWithCond(searchCommand)
            .ifEmpty { throw NoDataFoundException("No products found for search command: ${searchCommand}.") }
    }

    override fun save(product: Product) {
        this.productRepository.save(product)
    }

    override fun saveAll(products: List<Product>): Int {
        return this.productRepository.saveAll(products)
    }

    override fun deleteAll(): Int {
        return this.productRepository.deleteAll()
    }

    @Transactional
    override fun update(updateCommands: List<ProductUpdateCommand>): Int {
        return updateCommands.map {
            Product.updateAll(it)
        }.let { this.productRepository.update(it) }
    }
}