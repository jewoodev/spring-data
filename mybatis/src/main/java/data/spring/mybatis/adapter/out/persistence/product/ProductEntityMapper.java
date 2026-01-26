package data.spring.mybatis.adapter.out.persistence.product;

import data.spring.mybatis.application.service.product.command.ProductSearchCommand;
import data.spring.mybatis.application.service.product.command.ProductUpdateCommand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductEntityMapper {
    void save(ProductEntity entity);

    int saveAll(List<ProductEntity> entities);

    void update(ProductUpdateCommand updateCommand);

    Optional<ProductEntity> findById(Long productId);

    List<ProductEntity> findAll(ProductSearchCommand searchCond);

    int deleteAll();
}
