package data.spring.mybatis.adapter.out.persistence;

import data.spring.mybatis.application.required.ProductRepository;
import data.spring.mybatis.application.service.command.ProductSearchCommand;
import data.spring.mybatis.application.service.command.ProductUpdateCommand;

import java.util.List;
import java.util.Optional;

public class ProductPersister implements ProductRepository {
    private final ProductMapper productMapper;

    public ProductPersister(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public void save(ProductEntity entity) {
        this.productMapper.save(entity);
    }

    @Override
    public int saveAll(List<ProductEntity> entities) {
        return this.productMapper.saveAll(entities);
    }

    @Override
    public void update(ProductUpdateCommand updateCommand) {
        this.productMapper.update(updateCommand);
    }

    @Override
    public Optional<ProductEntity> findById(Long productId) {
        return this.productMapper.findById(productId);
    }

    @Override
    public List<ProductEntity> findAll(ProductSearchCommand searchCond) {
        return this.productMapper.findAll(searchCond);
    }

    @Override
    public int deleteAll() {
        return this.productMapper.deleteAll();
    }
}
