package data.spring.mybatis

import data.spring.mybatis.adapter.`in`.email.EmailConfig
import data.spring.mybatis.adapter.`in`.member.MemberAdapterConfig
import data.spring.mybatis.application.ApplicationConfig
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(DataAccessTestConfig::class, RepositoryTestConfig::class,
    ApplicationConfig::class,
    MemberAdapterConfig::class, EmailConfig::class)
class IntegrationTestConfig {
}