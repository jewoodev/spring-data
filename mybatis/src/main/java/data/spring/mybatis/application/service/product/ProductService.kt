package data.spring.mybatis.application.service.product

import data.spring.mybatis.application.provided.product.ProductUseCase
import data.spring.mybatis.application.required.product.ProductRepository
import data.spring.mybatis.application.service.product.command.ProductSearchCommand
import data.spring.mybatis.application.service.product.command.ProductUpdateCommand
import data.spring.mybatis.application.exception.NoDataFoundException
import data.spring.mybatis.domain.product.Product
import org.springframework.transaction.annotation.Transactional

open class ProductService(
    val productRepository: ProductRepository
): ProductUseCase {
    override fun findById(productId: Long): Product?
        = this.productRepository.findById(productId) ?: throw NoDataFoundException("Product not found with id: ${productId}.")

    override fun findAll(searchCommand: ProductSearchCommand): List<Product>
        = this.productRepository.findAll(searchCommand)
            .ifEmpty { throw NoDataFoundException("No products found for search command: ${searchCommand}.") }

    override fun save(product: Product)
        = this.productRepository.save(product)

    override fun saveAll(products: List<Product>): Int
        = this.productRepository.saveAll(products)

    override fun update(updateCommand: ProductUpdateCommand)
        = this.productRepository.update(updateCommand)

    @Transactional
    override fun updateAll(updateCommands: List<ProductUpdateCommand>): Int
        = updateCommands.onEach(this.productRepository::update).count()
}