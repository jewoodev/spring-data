package data.spring.mybatis.adapter.`in`.product.dto

import data.spring.mybatis.application.service.product.command.ProductUpdateCommand
import jakarta.validation.Valid

data class ProductUpdateBatchRequest(
    @field:Valid
    val updateRequests: List<ProductUpdateRequest>
) {
    fun toCommands(): List<ProductUpdateCommand> {
        return this.updateRequests.stream()
            .map { request ->
                ProductUpdateCommand(
                    productId = request.productId,
                    productName = request.productName,
                    price = request.price,
                    quantity = request.quantity
                )
            }
            .toList()
    }
}
