package data.spring.mybatis.adapter.`in`.product.request

import data.spring.mybatis.application.service.product.command.ProductUpdateCommand
import jakarta.validation.Valid

data class ProductUpdateBatchRequest(
    @field:Valid
    val updateRequests: List<ProductUpdateRequest>
) {
    fun toCommands(): List<ProductUpdateCommand> {
        return this.updateRequests
            .map {
                ProductUpdateCommand(
                    productId = it.productId,
                    productName = it.productName,
                    price = it.price,
                    quantity = it.quantity
                )
            }
    }
}
