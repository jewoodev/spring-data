package data.spring.mybatis;

import data.spring.mybatis.adapter.out.persistence.ProductMapper;
import data.spring.mybatis.application.required.ProductRepository;
import data.spring.mybatis.application.required.ProductUseCase;
import data.spring.mybatis.application.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MapperTestConfig.class)
public class IntegrationTestConfig {
    @Bean
    public ProductRepository productRepository(ProductMapper productMapper) {
        return productMapper;
    }

    @Bean
    public ProductUseCase productUseCase(ProductRepository productRepository) {
        return new ProductService(productRepository);
    }
}
