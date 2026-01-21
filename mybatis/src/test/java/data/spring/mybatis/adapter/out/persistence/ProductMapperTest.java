package data.spring.mybatis.adapter.out.persistence;

import data.spring.mybatis.MapperTestSupport;
import data.spring.mybatis.adapter.in.dto.ProductSearchCond;
import data.spring.mybatis.adapter.in.dto.ProductUpdateDto;
import data.spring.mybatis.domain.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperTest extends MapperTestSupport {
    @AfterEach
    void tearDown() {
        super.productMapper.deleteAll();
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

        super.productMapper.update(1L, ProductUpdateDto.of("리얼 유어바티스", 30_000, 100));
        savedProduct = super.productMapper.findById(1L).get();
        assertThat(savedProduct.getProductName()).isEqualTo("리얼 유어바티스");
    }
}