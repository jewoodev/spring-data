package data.spring.mybatis.domain;

public class Product {
    private Long productId;
    private String productName;
    private int price;
    private int quantity;

    public Product() {
    }

    private Product(String productName, int price, int quantity) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public static Product of(String productName, int price, int quantity) {
        return new Product(productName, price, quantity);
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
