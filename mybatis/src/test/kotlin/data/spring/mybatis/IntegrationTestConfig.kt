package data.spring.mybatis

import data.spring.mybatis.adapter.`in`.email.EmailConfig
import data.spring.mybatis.adapter.`in`.member.MemberAdapterConfig
import data.spring.mybatis.application.ApplicationConfig
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(RepositoryTestConfig::class, // data access
    ApplicationConfig::class, // application
    MemberAdapterConfig::class, EmailConfig::class) // adapter
class IntegrationTestConfig {
}