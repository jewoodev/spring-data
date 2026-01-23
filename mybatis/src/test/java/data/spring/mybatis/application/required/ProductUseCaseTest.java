package data.spring.mybatis.application.required;

import data.spring.mybatis.IntegrationTestSupport;
import data.spring.mybatis.adapter.in.dto.ProductSearchCond;
import data.spring.mybatis.adapter.in.dto.ProductUpdateBatchRequest;
import data.spring.mybatis.adapter.in.dto.ProductUpdateRequest;
import data.spring.mybatis.domain.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductUseCaseTest extends IntegrationTestSupport {
    @AfterEach
    void tearDown() {
        super.productMapper.deleteAll();
    }

    @Test
    void updateProductsSuccessfully() {
        // given
        var products = List.of(
                Product.of("상품1", 20_000, 10),
                Product.of("상품2", 30_000, 20),
                Product.of("상품3", 40_000, 30)
        );
        super.productMapper.saveAll(products);

        var updateBatch = new ProductUpdateBatchRequest(
                List.of(
                ProductUpdateRequest.of(1L, "상품4", null, null),
                ProductUpdateRequest.of(2L, "상품5", null, null),
                ProductUpdateRequest.of(3L, "상품6", null, null)
                )
        );

        var sut = super.productUseCase;

        // when
        sut.updateAll(updateBatch);

        // then
        List<Product> savedProducts = productMapper.findAll(ProductSearchCond.of(null, null));
        assertThat(savedProducts).extracting("productName").containsExactly("상품4", "상품5", "상품6");
    }
}