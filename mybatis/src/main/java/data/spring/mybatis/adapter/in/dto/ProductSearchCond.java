package data.spring.mybatis.adapter.in.dto;

public record ProductSearchCond(
        String productName,
        Integer maxPrice
) {
    public static ProductSearchCond of(String productName, Integer maxPrice) {
        return new ProductSearchCond(productName, maxPrice);
    }
}
