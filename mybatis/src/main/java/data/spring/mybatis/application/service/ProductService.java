package data.spring.mybatis.application.service;

import data.spring.mybatis.application.required.ProductRepository;
import data.spring.mybatis.application.required.ProductUseCase;
import data.spring.mybatis.application.service.command.ProductSearchCommand;
import data.spring.mybatis.application.service.command.ProductUpdateCommand;
import data.spring.mybatis.domain.Product;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class ProductService implements ProductUseCase {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return this.productRepository.findById(productId);
    }

    @Override
    public List<Product> findAll(ProductSearchCommand searchCommand) {
        return this.productRepository.findAll(searchCommand);
    }

    @Override
    public void save(Product product) {
        this.productRepository.save(product);
    }

    @Transactional
    @Override
    public int saveAll(List<Product> entities) {
        return this.productRepository.saveAll(entities);
    }

    @Override
    public void update(ProductUpdateCommand updateCommand) {
        this.productRepository.update(updateCommand);
    }

    @Transactional
    @Override
    public int updateAll(List<ProductUpdateCommand> updateCommands) {
        int cnt = 0;
        for (ProductUpdateCommand command : updateCommands) {
            this.productRepository.update(command);
            cnt++;
        }
        return cnt;
    }
}
