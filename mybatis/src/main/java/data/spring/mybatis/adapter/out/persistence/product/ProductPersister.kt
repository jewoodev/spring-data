package data.spring.mybatis.adapter.out.persistence.product

import data.spring.mybatis.application.required.product.ProductRepository
import data.spring.mybatis.application.service.product.command.ProductSearchCommand
import data.spring.mybatis.application.service.product.command.ProductUpdateCommand
import data.spring.mybatis.domain.product.Product
import java.util.*

class ProductPersister(
    val productEntityMapper: ProductEntityMapper
): ProductRepository {
    override fun save(product: Product)
        = this.productEntityMapper.save(ProductEntity.fromDomain(product))

    override fun saveAll(products: List<Product>): Int
        = this.productEntityMapper.saveAll(
            products.map(ProductEntity::fromDomain))

    override fun update(updateCommand: ProductUpdateCommand)
        = this.productEntityMapper.update(updateCommand)

    override fun findById(productId: Long): Product?
        = this.productEntityMapper.findById(productId)?.toDomain()

    override fun findAll(searchCommand: ProductSearchCommand): List<Product>
        = this.productEntityMapper.findAll(searchCommand)
            .map(ProductEntity::toDomain)

    override fun deleteAll(): Int
        = this.productEntityMapper.deleteAll()
}