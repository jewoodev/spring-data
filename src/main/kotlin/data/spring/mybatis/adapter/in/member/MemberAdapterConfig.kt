package data.spring.mybatis.adapter.`in`.member

import data.spring.mybatis.adapter.`in`.member.verify.SimpleEmailVerifier
import data.spring.mybatis.adapter.`in`.member.verify.SimpleEmailVerifierCache
import data.spring.mybatis.adapter.`in`.member.verify.SimpleMemberDuplicationVerifier
import data.spring.mybatis.application.required.member.MemberRepository
import data.spring.mybatis.domain.member.EmailVerifier
import data.spring.mybatis.domain.member.EmailVerifierCache
import data.spring.mybatis.domain.member.MemberDuplicationVerifier
import data.spring.mybatis.domain.member.PasswordEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MemberAdapterConfig {
    @Bean
    fun memberDuplicationVerifier(memberRepository: MemberRepository): MemberDuplicationVerifier {
        return SimpleMemberDuplicationVerifier(memberRepository)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return SimplePasswordEncoder()
    }

    @Bean
    fun emailVerifier(evcCache: EmailVerifierCache): EmailVerifier {
        return SimpleEmailVerifier(evcCache)
    }

    @Bean
    fun evcCache(): EmailVerifierCache {
        return SimpleEmailVerifierCache()
    }
}