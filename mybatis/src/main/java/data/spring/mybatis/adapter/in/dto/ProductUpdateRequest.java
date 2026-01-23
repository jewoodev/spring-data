package data.spring.mybatis.adapter.in.dto;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductUpdateRequest(
        @NotNull(message = "프로그램 내부 오류입니다. 관리자에게 알려주세요.")
        Long productId,

        @Size(min = 2, max = 100, message = "상품명은 2자 이상 100자 이하로 입력해주세요.")
        String productName,

        Integer price,
        Integer quantity
) {
    @AssertFalse(message = "수정할 정보가 없습니다.")
    public boolean isEmpty() {
        return productName == null && price == null && quantity == null;
    }

    public static ProductUpdateRequest of(Long productId, String productName, Integer price, Integer quantity) {
        return new ProductUpdateRequest(productId, productName, price, quantity);
    }
}
