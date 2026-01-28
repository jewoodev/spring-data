package data.spring.mybatis.adapter.out.persistence.product

import data.spring.mybatis.application.required.product.ProductRepository
import data.spring.mybatis.application.service.product.command.ProductSearchCommand
import data.spring.mybatis.application.service.product.command.ProductUpdateCommand
import data.spring.mybatis.domain.product.Product
import java.util.*

class ProductPersister(
    val productEntityMapper: ProductEntityMapper
): ProductRepository {
    override fun save(product: Product) {
        this.productEntityMapper.save(ProductEntity.fromDomain(product))
    }

    override fun saveAll(products: List<Product>): Int {
        return this.productEntityMapper.saveAll(
            products.stream().map(ProductEntity::fromDomain).toList()
        )
    }

    override fun update(updateCommand: ProductUpdateCommand) {
        this.productEntityMapper.update(updateCommand)
    }

    override fun findById(productId: Long): Optional<Product> {
        return this.productEntityMapper.findById(productId).map(ProductEntity::toDomain)
    }

    override fun findAll(searchCommand: ProductSearchCommand): List<Product> {
        return this.productEntityMapper.findAll(searchCommand).stream()
            .map(ProductEntity::toDomain).toList()
    }

    override fun deleteAll(): Int {
        return this.productEntityMapper.deleteAll()
    }
}