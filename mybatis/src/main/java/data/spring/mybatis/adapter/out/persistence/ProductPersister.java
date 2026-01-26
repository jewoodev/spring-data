package data.spring.mybatis.adapter.out.persistence;

import data.spring.mybatis.application.required.ProductRepository;
import data.spring.mybatis.application.service.command.ProductSearchCommand;
import data.spring.mybatis.application.service.command.ProductUpdateCommand;
import data.spring.mybatis.domain.Product;

import java.util.List;
import java.util.Optional;

public class ProductPersister implements ProductRepository {
    private final ProductEntityMapper productEntityMapper;

    public ProductPersister(ProductEntityMapper productEntityMapper) {
        this.productEntityMapper = productEntityMapper;
    }

    @Override
    public void save(Product product) {
        this.productEntityMapper.save(ProductEntity.fromDomain(product));
    }

    @Override
    public int saveAll(List<Product> products) {
        return this.productEntityMapper.saveAll(products.stream().map(ProductEntity::fromDomain).toList());
    }

    @Override
    public void update(ProductUpdateCommand updateCommand) {
        this.productEntityMapper.update(updateCommand);
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return this.productEntityMapper.findById(productId).map(ProductEntity::toDomain);
    }

    @Override
    public List<Product> findAll(ProductSearchCommand searchCond) {
        return this.productEntityMapper.findAll(searchCond).stream().map(ProductEntity::toDomain).toList();
    }

    @Override
    public int deleteAll() {
        return this.productEntityMapper.deleteAll();
    }
}
