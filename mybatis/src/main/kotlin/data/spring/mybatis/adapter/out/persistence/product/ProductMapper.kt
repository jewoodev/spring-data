package data.spring.mybatis.adapter.out.persistence.product

import data.spring.mybatis.application.service.product.command.ProductSearchCommand
import data.spring.mybatis.application.service.product.command.ProductUpdateCommand
import data.spring.mybatis.domain.product.Product
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ProductMapper {
    fun save(product: Product)

    fun saveAll(products: List<Product>): Int

    fun update(updateCommand: ProductUpdateCommand): Int

    fun findById(productId: Long): Product?

    fun findWithCond(searchCommand: ProductSearchCommand): List<Product>

    fun deleteAll(): Int
}