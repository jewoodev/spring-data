package data.spring.mybatis.adapter.out.persistence.product

import data.spring.mybatis.application.service.product.command.ProductSearchCond
import data.spring.mybatis.domain.product.Product
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import java.time.LocalDateTime

@Mapper
interface ProductMapper {
    fun save(product: Product): Int

    fun update(product: Product): Int

    fun truncate(): Int

    fun findById(productId: Long): Product?
    fun findByCond(@Param("searchCond") searchCond: ProductSearchCond?,
                   @Param("createdAt") createdAt: LocalDateTime?,
                   @Param("cursor") cursor: Long?,
                   @Param("size") size: Int): List<Product>
    fun findAll(): List<Product>
}