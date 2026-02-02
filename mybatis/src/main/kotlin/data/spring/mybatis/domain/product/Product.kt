package data.spring.mybatis.domain.product

import data.spring.mybatis.domain.clock
import java.time.LocalDateTime

data class Product constructor(
    val productId: Long? = null,
    val productName: String,
    val price: Int,
    val quantity: Int,
    val createdAt: LocalDateTime = LocalDateTime.now(clock()),
    var updatedAt: LocalDateTime = LocalDateTime.now(clock()),
)
