package data.spring.mybatis.application.service.product.command

import data.spring.mybatis.domain.clock
import java.time.LocalDateTime

class ProductUpdateCommand(
    val productId: Long,
    val productName: String?,
    val price: Int?,
    val quantity: Int?,
) {
    var updatedAt: LocalDateTime? = null

    fun isUpdated(): Boolean {
        return productName != null || price != null || quantity != null
    }

    fun whenUpdated(): ProductUpdateCommand {
        updatedAt = LocalDateTime.now(clock())
        return this
    }
}
