package data.spring.mybatis.adapter.in.product.dto;

public record ProductSearchRequest(
        String productName,
        Integer maxPrice
) {
}
