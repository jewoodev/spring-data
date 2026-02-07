package data.spring.mybatis.application.service.product

import data.spring.mybatis.application.exception.NoDataFoundException
import data.spring.mybatis.application.provided.product.ProductUseCase
import data.spring.mybatis.application.required.product.ProductRepository
import data.spring.mybatis.application.service.product.command.ProductSearchCond
import data.spring.mybatis.domain.product.Product
import data.spring.mybatis.application.service.product.command.ProductUpdateCommand
import data.spring.mybatis.domain.product.Price
import data.spring.mybatis.domain.product.ProductName
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

open class ProductService(
    val productRepository: ProductRepository
) : ProductUseCase {
    override fun save(product: Product): Int {
        return productRepository.save(product)
    }

    @Transactional
    override fun saveAll(products: List<Product>): Int {
        return products.sumOf { productRepository.save(it) }
    }

    @Transactional
    override fun update(updateCommand: ProductUpdateCommand): Int {
        return updateCommand.let {
                findById(it.productId).updateInfo(
                    it.productName?.let { value -> ProductName(value) },
                    it.price?.let { amount -> Price(amount) }
                )
        }.let { productRepository.save(it) }
    }

    @Transactional
    override fun updateAll(updateCommands: List<ProductUpdateCommand>): Int {
        return updateCommands.sumOf { update(it) }
    }

    override fun deleteAll(): Int {
        return productRepository.truncate()
    }

    override fun findById(productId: Long): Product {
        return productRepository.findById(productId) ?: throw NoDataFoundException("해당 식별자를 갖는 상품이 없습니다: ${productId}.")
    }

    override fun findByCond(
        searchCond: ProductSearchCond?,
        createdAt: LocalDateTime?,
        productId: Long?,
        size: Int
    ): List<Product> {
        return productRepository.findByCond(searchCond, createdAt, productId, size)
            .ifEmpty { throw NoDataFoundException("해당 검색 조건을 만족하는 상품이 없습니다: ${searchCond}.") }
    }

    override fun findAll(): List<Product> {
        return productRepository.findAll()
            .ifEmpty { throw NoDataFoundException("저장된 상품이 없습니다.") }
    }
}