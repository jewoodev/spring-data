package data.spring.mybatis.adapter.out.persistence.product

import data.spring.mybatis.application.required.product.ProductRepository
import data.spring.mybatis.application.service.product.command.ProductSearchCommand
import data.spring.mybatis.domain.product.Product

class ProductPersister(
    val productMapper: ProductMapper,
): ProductRepository {
    override fun save(product: Product) {
        this.productMapper.save(product)
    }

    override fun saveAll(products: List<Product>): Int {
        return this.productMapper.saveAll(products)
    }

    override fun findById(productId: Long): Product? {
        return this.productMapper.findById(productId)
    }

    override fun findWithCond(searchCommand: ProductSearchCommand): List<Product> {
        return this.productMapper.findWithCond(searchCommand)
    }

    override fun update(products: List<Product>): Int {
        var cnt = 0
        products.filter { it.isUpdated() }
            .forEach { cnt += this.productMapper.update(it) }
        return cnt
    }

    override fun deleteAll(): Int {
        return this.productMapper.deleteAll()
    }
}