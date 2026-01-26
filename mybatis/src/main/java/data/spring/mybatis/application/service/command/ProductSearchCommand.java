package data.spring.mybatis.application.service.command;

public record ProductSearchCommand(
        String productName,
        Integer maxPrice
) {
    public static ProductSearchCommand of(String productName, Integer maxPrice) {
        return new ProductSearchCommand(productName, maxPrice);
    }
}
