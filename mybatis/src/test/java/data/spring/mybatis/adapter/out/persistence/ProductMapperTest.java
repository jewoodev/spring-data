package data.spring.mybatis.adapter.out.persistence;

import data.spring.mybatis.MapperTestSupport;
import data.spring.mybatis.adapter.in.dto.ProductSearchCond;
import data.spring.mybatis.adapter.in.dto.ProductUpdateRequest;
import data.spring.mybatis.domain.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperTest extends MapperTestSupport {
    @AfterEach
    void tearDown() {
        super.productMapper.deleteAll();
    }

    @Test
    void saveProducts() {
        var products = List.of(
                Product.of("상품1", 20_000, 10),
                Product.of("상품2", 30_000, 20),
                Product.of("상품3", 40_000, 30)
        );

        super.productMapper.saveAll(products);

        List<Product> savedProducts = super.productMapper.findAll(ProductSearchCond.of(null, null));
        assertThat(savedProducts).hasSize(3);
        assertThat(savedProducts).extracting("productName").contains("상품1", "상품2", "상품3");
    }

    @Test
    void saveAndFindById() {
        var product = Product.of("리얼 마이바티스", 30_000, 100);
        super.productMapper.save(product);

        var savedProduct = super.productMapper.findById(1L).get();
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getProductName()).isEqualTo(product.getProductName());
        assertThat(savedProduct.getPrice()).isEqualTo(product.getPrice());
        assertThat(savedProduct.getQuantity()).isEqualTo(product.getQuantity());
    }

    @Test
    void saveAndFindAll() {
        var product1 = Product.of("리얼 마이바티스", 30_000, 100);
        super.productMapper.save(product1);
        var product2 = Product.of("리얼 제이디비씨", 30_000, 100);
        super.productMapper.save(product2);

        var savedProducts = super.productMapper.findAll(new ProductSearchCond(null, 30_000));
        assertThat(savedProducts).hasSize(2);
        assertThat(savedProducts).extracting("productName").contains("리얼 마이바티스", "리얼 제이디비씨");
    }

    @Test
    void update() {
        var product = Product.of("리얼 마이바티스", 30_000, 100);
        super.productMapper.save(product);

        var savedProduct = super.productMapper.findById(1L).get();
        assertThat(savedProduct.getProductName()).isEqualTo(product.getProductName());

        Long productId = savedProduct.getProductId();
        super.productMapper.update(ProductUpdateRequest.of(productId, "리얼 유어바티스", 30_000, 100));
        savedProduct = super.productMapper.findById(productId).get();
        assertThat(savedProduct.getProductName()).isEqualTo("리얼 유어바티스");
    }
}