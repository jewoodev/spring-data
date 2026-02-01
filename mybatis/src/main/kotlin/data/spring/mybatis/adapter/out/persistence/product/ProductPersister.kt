package data.spring.mybatis.adapter.out.persistence.product

import data.spring.mybatis.application.required.product.ProductRepository
import data.spring.mybatis.application.service.product.command.ProductSearchCommand
import data.spring.mybatis.application.service.product.command.ProductUpdateCommand
import data.spring.mybatis.domain.product.Product

class ProductPersister(
    val productEntityMapper: ProductEntityMapper,
): ProductRepository {
    override fun save(product: Product) {
        this.productEntityMapper.save(ProductEntity.fromDomain(product))
    }

    override fun saveAll(products: List<Product>): Int {
        return this.productEntityMapper.saveAll(products.map(ProductEntity::fromDomain))
    }

    override fun findById(productId: Long): Product? {
        return this.productEntityMapper.findById(productId)?.toDomain()
    }

    override fun findWithCond(searchCommand: ProductSearchCommand): List<Product> {
        return this.productEntityMapper.findWithCond(searchCommand)
            .map(ProductEntity::toDomain)
    }

    override fun update(updateCommands: List<ProductUpdateCommand>) {
        return updateCommands.filter { it.isUpdated() }
            .forEach { this.productEntityMapper.update(it.whenUpdated()) }
    }

    override fun deleteAll(): Int {
        return this.productEntityMapper.deleteAll()
    }
}