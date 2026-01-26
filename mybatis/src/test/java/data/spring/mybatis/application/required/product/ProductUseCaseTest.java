package data.spring.mybatis.application.required.product;

import data.spring.mybatis.IntegrationTestSupport;
import data.spring.mybatis.application.service.product.command.ProductSearchCommand;
import data.spring.mybatis.application.service.product.command.ProductUpdateCommand;
import data.spring.mybatis.domain.product.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductUseCaseTest extends IntegrationTestSupport {
    @AfterEach
    void tearDown() {
        super.productRepository.deleteAll();
    }

    @Test
    void saveProductsSuccessfully() {
        var products = List.of(
                Product.of("상품1", 20_000, 10),
                Product.of("상품2", 30_000, 20),
                Product.of("상품3", 40_000, 30)
        );
        var sut = super.productUseCase;
        sut.saveAll(products);
        var expected = List.of(
                Product.of(1L, "상품1", 20_000, 10),
                Product.of(2L, "상품2", 30_000, 20),
                Product.of(3L, "상품3", 40_000, 30)
        );

        var savedProducts = sut.findAll(ProductSearchCommand.of(null, null));

        assertThat(savedProducts).isEqualTo(expected);
    }

    @Test
    void findProductSuccessfully() {
        var product = Product.of( "리얼 마이바티스", 30_000, 100);
        var sut = super.productUseCase;
        sut.save(product);
        var expected = Product.of(1L, "리얼 마이바티스", 30_000, 100);

        var savedProduct = sut.findById(1L).get();

        assertThat(savedProduct).isEqualTo(expected);
    }

    @Test
    void findProductsSuccessfully() {
        var products = List.of(
                Product.of("리얼 마이바티스", 30_000, 100),
                Product.of("리얼 제이디비씨", 30_000, 100),
                Product.of("리얼 비쌈", 50_000, 100)
        );
        var sut = super.productUseCase;
        sut.saveAll(products);

        var savedProducts = sut.findAll(new ProductSearchCommand(null, 30_000));
        assertThat(savedProducts).hasSize(2);
        assertThat(savedProducts).extracting("productName").contains("리얼 마이바티스", "리얼 제이디비씨");
    }

    @Test
    void updateProductsSuccessfully() {
        // given
        var products = List.of(
                Product.of("상품1", 20_000, 10),
                Product.of("상품2", 30_000, 20),
                Product.of("상품3", 40_000, 30)
        );
        super.productRepository.saveAll(products);

        var updateCommands = List.of(
                ProductUpdateCommand.of(1L, "상품4", null, null),
                ProductUpdateCommand.of(2L, "상품5", null, null),
                ProductUpdateCommand.of(3L, "상품6", null, null)
        );
        var sut = super.productUseCase;

        // when
        sut.updateAll(updateCommands);

        // then
        var savedProducts = super.productRepository.findAll(ProductSearchCommand.of(null, null));
        assertThat(savedProducts).extracting("productName").containsExactly("상품4", "상품5", "상품6");
    }
}