package data.spring.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@MapperScan("data.spring.mybatis.adapter.out.persistence")
public class MapperTestConfig {
    @Bean
    public DataSource datasource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {
        var factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath*:data/spring/mybatis/adapter/out/persistence/*.xml");
        for (Resource resource : resources) {
            System.out.println("xml 가져온 것은 ! = " + resource.getFilename());
        }
        factoryBean.setMapperLocations(
                resources
        );

        factoryBean.setTypeAliasesPackage("data.spring.mybatis.domain");

        var conf = new org.apache.ibatis.session.Configuration();
        conf.setMapUnderscoreToCamelCase(true);

        factoryBean.setConfiguration(conf);

        return factoryBean.getObject();
    }
}
