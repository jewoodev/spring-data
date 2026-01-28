package data.spring.mybatis.adapter.`in`.product.dto

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
    companion object {
        fun of(productId: Long, productName: String, price: Int, quantity: Int): ProductUpdateRequest
            = ProductUpdateRequest(productId, productName, price, quantity)
    }

    @AssertFalse(message = "수정할 정보가 없습니다.")
    fun isEmpty(): Boolean
        = productName == null && price == null && quantity == null
}
