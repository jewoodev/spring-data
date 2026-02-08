package data.spring.mybatis.application

import data.spring.mybatis.application.provided.member.MemberUseCase
import data.spring.mybatis.application.provided.product.ProductUseCase
import data.spring.mybatis.application.required.member.MemberRepository
import data.spring.mybatis.application.required.product.ProductRepository
import data.spring.mybatis.application.service.member.MemberService
import data.spring.mybatis.application.service.product.ProductService
import data.spring.mybatis.domain.email.EmailSender
import data.spring.mybatis.domain.member.EmailVerifier
import data.spring.mybatis.domain.member.MemberDuplicationVerifier
import data.spring.mybatis.domain.member.PasswordEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfig {
    @Bean
    fun productUseCase(productRepository: ProductRepository): ProductUseCase {
        return ProductService(productRepository)
    }

    @Bean
    fun memberUseCase(
        memberRepository: MemberRepository, passwordEncoder: PasswordEncoder,
        memberDuplicationVerifier: MemberDuplicationVerifier, emailVerifier: EmailVerifier,
        emailSender: EmailSender
    ): MemberUseCase {
        return MemberService(memberRepository = memberRepository, passwordEncoder = passwordEncoder,
            memberDuplicationVerifier = memberDuplicationVerifier, emailVerifier = emailVerifier,
            emailSender = emailSender)
    }
}