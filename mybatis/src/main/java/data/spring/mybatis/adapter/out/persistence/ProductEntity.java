package data.spring.mybatis.adapter.out.persistence;

import data.spring.mybatis.domain.Product;

public class ProductEntity {
    private Long productId;
    private String productName;
    private int price;
    private int quantity;

    public ProductEntity() {
    }

    private ProductEntity(String productName, int price, int quantity) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public static ProductEntity of(String productName, int price, int quantity) {
        return new ProductEntity(productName, price, quantity);
    }

    public static ProductEntity fromDomain(Product product) {
        return new ProductEntity(product.productName(), product.price(), product.quantity());
    }

    public Product toDomain() {
        return new Product(this.productId, this.productName, this.price, this.quantity);
    }

     // ============= getter =============
    public Long getProductId() {
        return this.productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public int getPrice() {
        return this.price;
    }

    public int getQuantity() {
        return this.quantity;
    }
}
