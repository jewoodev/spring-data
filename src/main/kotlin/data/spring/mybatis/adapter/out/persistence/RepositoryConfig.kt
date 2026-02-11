package data.spring.mybatis.adapter.out.persistence

import data.spring.mybatis.adapter.out.persistence.member.MemberMapper
import data.spring.mybatis.adapter.out.persistence.member.MemberMaBatisRepository
import data.spring.mybatis.adapter.out.persistence.product.ProductMapper
import data.spring.mybatis.adapter.out.persistence.product.ProductPersister
import data.spring.mybatis.application.required.member.MemberRepository
import data.spring.mybatis.application.required.product.ProductRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RepositoryConfig {
    @Bean
    fun productRepository(productMapper: ProductMapper): ProductRepository {
        return ProductPersister(productMapper)
    }

    @Bean
    fun memberRepository(memberMapper: MemberMapper): MemberRepository {
        return MemberMaBatisRepository(memberMapper)
    }
}