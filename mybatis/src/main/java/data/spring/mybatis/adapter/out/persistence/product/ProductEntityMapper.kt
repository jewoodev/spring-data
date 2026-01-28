package data.spring.mybatis.adapter.out.persistence.product

import data.spring.mybatis.application.service.product.command.ProductSearchCommand
import data.spring.mybatis.application.service.product.command.ProductUpdateCommand
import org.apache.ibatis.annotations.Mapper
import java.util.*

@Mapper
interface ProductEntityMapper {
    fun save(entity: ProductEntity)

    fun saveAll(entities: List<ProductEntity>): Int

    fun update(updateCommand: ProductUpdateCommand)

    fun findById(productId: Long): Optional<ProductEntity>

    fun findAll(searchCond: ProductSearchCommand): List<ProductEntity>

    fun deleteAll(): Int
}