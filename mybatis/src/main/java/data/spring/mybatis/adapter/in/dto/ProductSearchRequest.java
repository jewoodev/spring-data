package data.spring.mybatis.adapter.in.dto;

public record ProductSearchRequest(
        String productName,
        Integer maxPrice
) {
}
