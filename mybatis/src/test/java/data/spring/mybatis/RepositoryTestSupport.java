package data.spring.mybatis;

import data.spring.mybatis.application.required.ProductRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RepositoryTestConfig.class)
public abstract class RepositoryTestSupport {
    @Autowired
    protected ProductRepository productRepository;
}
