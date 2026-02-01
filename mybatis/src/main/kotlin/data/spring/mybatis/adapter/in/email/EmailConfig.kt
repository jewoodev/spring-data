package data.spring.mybatis.adapter.`in`.email

import data.spring.mybatis.adapter.`in`.member.verify.SimpleEmailVerifierCache
import data.spring.mybatis.domain.email.EmailSender
import data.spring.mybatis.domain.member.EmailVerifierCache
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EmailConfig {
    @Bean
    fun emailSender(evcCache: EmailVerifierCache): EmailSender {
        return SimpleEmailSender(evcCache)
    }

    @Bean
    fun evcCache(): EmailVerifierCache {
        return SimpleEmailVerifierCache()
    }
}