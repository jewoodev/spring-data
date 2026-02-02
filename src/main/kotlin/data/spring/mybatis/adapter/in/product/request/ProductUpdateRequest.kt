package data.spring.mybatis.adapter.`in`.product.request

import data.spring.mybatis.domain.product.request.ProductUpdateCommand
import jakarta.validation.constraints.AssertFalse
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class ProductUpdateRequest(
    @field:NotNull(message = "프로그램 내부 오류입니다. 관리자에게 알려주세요.")
    val productId: Long,

    @field:Size(min = 2, max = 100, message = "상품명은 2자 이상 100자 이하로 입력해주세요.")
    val productName: String?,

    val price: Int?,
    val quantity: Int?
) {
    @AssertFalse(message = "수정할 정보가 없습니다.")
    fun isEmpty(): Boolean
        = productName == null && price == null && quantity == null

    fun toCommand(): ProductUpdateCommand {
        return ProductUpdateCommand(
            productId = productId,
            productName = productName,
            price = price,
            quantity = quantity
        )
    }
}
