package data.spring.mybatis

import data.spring.mybatis.adapter.`in`.member.SimplePasswordEncoder
import data.spring.mybatis.application.provided.member.MemberUseCase
import data.spring.mybatis.application.provided.product.ProductUseCase
import data.spring.mybatis.domain.member.EmailVerifierCache
import data.spring.mybatis.domain.member.PasswordEncoder
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableTransactionManagement
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [IntegrationTestConfig::class])
abstract class IntegrationTestSupport {
    @Autowired
    protected lateinit var productUseCase: ProductUseCase

    @Autowired
    protected lateinit var memberUseCase: MemberUseCase

    @Autowired
    protected lateinit var evcCache: EmailVerifierCache

    @Autowired
    protected lateinit var passwordEncoder: PasswordEncoder
}