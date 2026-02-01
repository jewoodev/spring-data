package data.spring.mybatis.adapter.out.persistence.product

import data.spring.mybatis.application.service.product.command.ProductSearchCommand
import data.spring.mybatis.application.service.product.command.ProductUpdateCommand
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ProductEntityMapper {
    fun save(entity: ProductEntity)

    fun saveAll(entities: List<ProductEntity>): Int

    fun update(updateCommand: ProductUpdateCommand): Int

    fun findById(productId: Long): ProductEntity?

    fun findWithCond(searchCommand: ProductSearchCommand): List<ProductEntity>

    fun deleteAll(): Int
}