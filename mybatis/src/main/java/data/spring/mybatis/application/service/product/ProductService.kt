package data.spring.mybatis.application.service.product

import data.spring.mybatis.application.provided.product.ProductUseCase
import data.spring.mybatis.application.required.product.ProductRepository
import data.spring.mybatis.application.service.product.command.ProductSearchCommand
import data.spring.mybatis.application.service.product.command.ProductUpdateCommand
import data.spring.mybatis.domain.product.Product
import org.springframework.transaction.annotation.Transactional
import java.util.*

open class ProductService(
    val productRepository: ProductRepository
): ProductUseCase {
    override fun findById(productId: Long): Optional<Product> {
        return this.productRepository.findById(productId)
    }

    override fun findAll(searchCommand: ProductSearchCommand): List<Product> {
        return this.productRepository.findAll(searchCommand)
    }

    override fun save(product: Product) {
        this.productRepository.save(product)
    }

    override fun saveAll(products: List<Product>): Int {
        return this.productRepository.saveAll(products)
    }

    override fun update(updateCommand: ProductUpdateCommand) {
        this.productRepository.update(updateCommand)
    }

    @Transactional
    override fun updateAll(updateCommands: List<ProductUpdateCommand>): Int {
        var cnt = 0
        for (command in updateCommands) {
            this.productRepository.update(command)
            cnt++
        }
        return cnt
    }
}