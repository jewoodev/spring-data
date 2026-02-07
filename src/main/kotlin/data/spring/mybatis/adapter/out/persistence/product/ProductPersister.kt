package data.spring.mybatis.adapter.out.persistence.product

import data.spring.mybatis.application.required.product.ProductRepository
import data.spring.mybatis.application.service.product.command.ProductSearchCond
import data.spring.mybatis.domain.product.Product
import java.time.LocalDateTime

class ProductPersister(
    val productMapper: ProductMapper,
) : ProductRepository {
    override fun save(product: Product): Int {
        return if (product.productId == null) {
            productMapper.save(product)
        } else {
            productMapper.update(product)
        }
    }

    override fun truncate(): Int {
        return productMapper.truncate()
    }

    override fun findById(productId: Long): Product? {
        return productMapper.findById(productId)
    }

    override fun findByCond(
        searchCond: ProductSearchCond?,
        createdAt: LocalDateTime?,
        productId: Long?,
        size: Int
    ): List<Product> {
        return productMapper.findByCond(searchCond, createdAt, productId, size)
    }

    override fun findAll(): List<Product> {
        return productMapper.findAll()
    }
}
