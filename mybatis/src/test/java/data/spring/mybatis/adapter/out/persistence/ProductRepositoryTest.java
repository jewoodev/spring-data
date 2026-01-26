package data.spring.mybatis.adapter.out.persistence;

import data.spring.mybatis.RepositoryTestSupport;
import data.spring.mybatis.application.service.command.ProductSearchCommand;
import data.spring.mybatis.application.service.command.ProductUpdateCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductRepositoryTest extends RepositoryTestSupport {
    @AfterEach
    void tearDown() {
        super.productRepository.deleteAll();
    }

    @Test
    void saveProductEntitys() {
        var entities = List.of(
                ProductEntity.of("상품1", 20_000, 10),
                ProductEntity.of("상품2", 30_000, 20),
                ProductEntity.of("상품3", 40_000, 30)
        );


        super.productRepository.saveAll(entities);

        var savedEntities = super.productRepository.findAll(ProductSearchCommand.of(null, null));
        assertThat(savedEntities).hasSize(3);
        assertThat(savedEntities).extracting("productName").contains("상품1", "상품2", "상품3");
    }

    @Test
    void saveAndFindById() {
        var entity = ProductEntity.of( "리얼 마이바티스", 30_000, 100);
        super.productRepository.save(entity);

        var savedProduct = super.productRepository.findById(1L).get();
        assertThat(savedProduct.getProductId()).isNotNull();
        assertThat(savedProduct.getProductName()).isEqualTo(entity.getProductName());
        assertThat(savedProduct.getPrice()).isEqualTo(entity.getPrice());
        assertThat(savedProduct.getQuantity()).isEqualTo(entity.getQuantity());
    }

    @Test
    void saveAndFindAll() {
        var createCommand1 = ProductEntity.of("리얼 마이바티스", 30_000, 100);
        super.productRepository.save(createCommand1);
        var createCommand2 = ProductEntity.of("리얼 제이디비씨", 30_000, 100);
        super.productRepository.save(createCommand2);

        var savedEntities = super.productRepository.findAll(new ProductSearchCommand(null, 30_000));
        assertThat(savedEntities).hasSize(2);
        assertThat(savedEntities).extracting("productName").contains("리얼 마이바티스", "리얼 제이디비씨");
    }

    @Test
    void update() {
        var entity = ProductEntity.of("리얼 마이바티스", 30_000, 100);
        super.productRepository.save(entity);

        var savedEntity = super.productRepository.findById(1L).get();
        assertThat(savedEntity.getProductName()).isEqualTo(entity.getProductName());

        Long productId = savedEntity.getProductId();
        super.productRepository.update(ProductUpdateCommand.of(productId, "리얼 유어바티스", 30_000, 100));
        savedEntity = super.productRepository.findById(productId).get();
        assertThat(savedEntity.getProductName()).isEqualTo("리얼 유어바티스");
    }
}