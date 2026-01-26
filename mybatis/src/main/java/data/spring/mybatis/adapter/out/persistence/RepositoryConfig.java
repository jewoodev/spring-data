package data.spring.mybatis.adapter.out.persistence;

import data.spring.mybatis.application.required.ProductRepository;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@MapperScan("data.spring.mybatis.adapter.out.persistence")
public class RepositoryConfig {
    @Bean
    public DataSource datasource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {
        var factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources("classpath*:data/spring/mybatis/adapter/out/persistence/*.xml")
        );

        factoryBean.setTypeAliasesPackage("data.spring.mybatis.adapter.out.persistence");

        var conf = new org.apache.ibatis.session.Configuration();
        conf.setMapUnderscoreToCamelCase(true);

        factoryBean.setConfiguration(conf);

        return factoryBean.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public ProductRepository productRepository(ProductEntityMapper productEntityMapper) {
        return new ProductPersister(productEntityMapper);
    }
}
