package data.spring.mybatis.application.service;

import data.spring.mybatis.adapter.out.persistence.ProductEntity;
import data.spring.mybatis.application.required.ProductRepository;
import data.spring.mybatis.application.required.ProductUseCase;
import data.spring.mybatis.application.service.command.ProductUpdateCommand;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ProductService implements ProductUseCase {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    @Override
    public int saveAll(List<ProductEntity> entities) {
        return productRepository.saveAll(entities);
    }

    @Transactional
    @Override
    public int updateAll(List<ProductUpdateCommand> updateCommands) {
        int cnt = 0;
        for (ProductUpdateCommand command : updateCommands) {
            productRepository.update(command);
            cnt++;
        }
        return cnt;
    }
}
