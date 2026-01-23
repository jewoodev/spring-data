package data.spring.mybatis.application.required;

import data.spring.mybatis.adapter.in.dto.ProductUpdateBatchRequest;
import data.spring.mybatis.domain.Product;

import java.util.List;

public interface ProductUseCase {
    int saveAll(List<Product> products);
    int updateAll(ProductUpdateBatchRequest updateBatchRequest);
}
