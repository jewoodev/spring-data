package data.spring.mybatis.adapter.in.dto;

public record ProductUpdateDto(
        String productName,
        int price,
        int quantity
) {
    public static ProductUpdateDto of(String productName, int price, int quantity) {
        return new ProductUpdateDto(productName, price, quantity);
    }
}
