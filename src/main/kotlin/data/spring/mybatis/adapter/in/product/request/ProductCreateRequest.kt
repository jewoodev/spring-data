package data.spring.mybatis.adapter.`in`.product.request

import data.spring.mybatis.application.provided.product.dto.ProductCreateCommand
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull

data class ProductCreateRequest(
    @field:NotNull
    @field:Size(min = 2, max = 100, message = "상품명은 2자 이상 100자 이하여야 합니다.")
    val productName: String,

    @field:Min(value = 1, message = "가격은 1원 이상이어야 합니다.")
    val price: Int,

    @field:Min(value = 0, message = "수량은 0개 이상이어야 합니다.")
    val quantity: Int
) {
    fun toCommand(): ProductCreateCommand {
        return ProductCreateCommand(productName, price, quantity)
    }
}
