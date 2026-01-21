package data.spring.mybatis.adapter.in.dto;

public record ProductSearchCond(
        String productName,
        Integer maxPrice
) {
}
