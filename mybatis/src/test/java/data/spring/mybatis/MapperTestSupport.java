package data.spring.mybatis;

import data.spring.mybatis.adapter.out.persistence.ProductMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MapperTestConfig.class)
public abstract class MapperTestSupport {
    @Autowired
    protected ProductMapper productMapper;
}
