package data.spring.mybatis.application;

import data.spring.mybatis.application.required.product.ProductRepository;
import data.spring.mybatis.application.required.product.ProductUseCase;
import data.spring.mybatis.application.service.product.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public ProductUseCase productUseCase(ProductRepository productRepository) {
        return new ProductService(productRepository);
    }
}
