package data.spring.mybatis;

import data.spring.mybatis.adapter.out.persistence.ProductMapper;
import data.spring.mybatis.application.required.ProductUseCase;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = IntegrationTestConfig.class)
public abstract class IntegrationTestSupport {
    @Autowired
    protected ProductMapper productMapper;

    @Autowired
    protected ProductUseCase productUseCase;
}
