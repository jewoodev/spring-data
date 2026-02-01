package data.spring.mybatis.adapter.out.persistence

import data.spring.mybatis.adapter.out.persistence.member.MemberEntityMapper
import data.spring.mybatis.adapter.out.persistence.member.MemberPersister
import data.spring.mybatis.adapter.out.persistence.product.ProductEntityMapper
import data.spring.mybatis.adapter.out.persistence.product.ProductPersister
import data.spring.mybatis.application.required.member.MemberRepository
import data.spring.mybatis.application.required.product.ProductRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RepositoryConfig {
    @Bean
    fun productRepository(productEntityMapper: ProductEntityMapper): ProductRepository {
        return ProductPersister(productEntityMapper)
    }

    @Bean
    fun memberRepository(memberEntityMapper: MemberEntityMapper): MemberRepository {
        return MemberPersister(memberEntityMapper)
    }
}