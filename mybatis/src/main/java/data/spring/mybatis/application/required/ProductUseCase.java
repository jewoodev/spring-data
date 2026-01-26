package data.spring.mybatis.application.required;

import data.spring.mybatis.adapter.out.persistence.ProductEntity;
import data.spring.mybatis.application.service.command.ProductUpdateCommand;

import java.util.List;

public interface ProductUseCase {
    int saveAll(List<ProductEntity> products);
    int updateAll(List<ProductUpdateCommand> updateBatchRequest);
}
