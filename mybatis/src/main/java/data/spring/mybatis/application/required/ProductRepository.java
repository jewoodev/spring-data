package data.spring.mybatis.application.required;

import data.spring.mybatis.adapter.out.persistence.ProductEntity;
import data.spring.mybatis.application.service.command.ProductSearchCommand;
import data.spring.mybatis.application.service.command.ProductUpdateCommand;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    void save(ProductEntity productEntity);

    int saveAll(List<ProductEntity> productEntities);

    void update(ProductUpdateCommand updateCommand);

    Optional<ProductEntity> findById(Long productId);

    List<ProductEntity> findAll(ProductSearchCommand searchCommand);

    int deleteAll();
}
