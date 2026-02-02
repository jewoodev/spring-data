package data.spring.mybatis

import data.spring.mybatis.adapter.out.persistence.RepositoryConfig
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Import(DataAccessTestConfig::class, RepositoryConfig::class)
@Configuration
class RepositoryTestConfig {
}