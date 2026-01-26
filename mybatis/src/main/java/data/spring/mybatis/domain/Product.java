package data.spring.mybatis.domain;


public record Product(
        long productId,
        String productName,
        int price,
        int quantity
) {
    public static Product of(long productId, String productName, int price, int quantity) {
        return new Product(productId, productName, price, quantity);
    }
}
