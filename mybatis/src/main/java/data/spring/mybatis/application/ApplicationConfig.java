package data.spring.mybatis.application;

import data.spring.mybatis.application.required.ProductRepository;
import data.spring.mybatis.application.required.ProductUseCase;
import data.spring.mybatis.application.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public ProductUseCase productUseCase(ProductRepository productRepository) {
        return new ProductService(productRepository);
    }
}
