package data.spring.mybatis.application.service;

import data.spring.mybatis.adapter.in.dto.ProductUpdateBatchRequest;
import data.spring.mybatis.adapter.in.dto.ProductUpdateRequest;
import data.spring.mybatis.application.required.ProductRepository;
import data.spring.mybatis.application.required.ProductUseCase;
import data.spring.mybatis.domain.Product;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ProductService implements ProductUseCase {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public int saveAll(List<Product> products) {
        return productRepository.saveAll(products);
    }

    @Override
    @Transactional
    public int updateAll(ProductUpdateBatchRequest updateBatchRequests) {
        int cnt = 0;
        for (ProductUpdateRequest updateDto : updateBatchRequests.getUpdateRequests()) {
            productRepository.update(updateDto);
            cnt++;
        }
        return cnt;
    }
}
