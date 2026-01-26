package data.spring.mybatis.application.required;

import data.spring.mybatis.IntegrationTestSupport;
import data.spring.mybatis.adapter.in.dto.ProductUpdateBatchRequest;
import data.spring.mybatis.adapter.in.dto.ProductUpdateRequest;
import data.spring.mybatis.adapter.out.persistence.ProductEntity;
import data.spring.mybatis.application.service.command.ProductSearchCommand;
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
    void updateProductsSuccessfully() {
        // given
        var entities = List.of(
                ProductEntity.of("상품1", 20_000, 10),
                ProductEntity.of("상품2", 30_000, 20),
                ProductEntity.of("상품3", 40_000, 30)
        );
        super.productRepository.saveAll(entities);

        var updateBatch = new ProductUpdateBatchRequest(
                List.of(
                ProductUpdateRequest.of(1L, "상품4", null, null),
                ProductUpdateRequest.of(2L, "상품5", null, null),
                ProductUpdateRequest.of(3L, "상품6", null, null)
                )
        );
        var updateCommands = updateBatch.toCommands();

        var sut = super.productUseCase;

        // when
        sut.updateAll(updateCommands);

        // then
        var savedProducts = super.productRepository.findAll(ProductSearchCommand.of(null, null));
        assertThat(savedProducts).extracting("productName").containsExactly("상품4", "상품5", "상품6");
    }
}