package data.spring.mybatis

import com.ninjasquad.springmockk.MockkBean
import data.spring.mybatis.adapter.`in`.member.MemberController
import data.spring.mybatis.adapter.`in`.product.ProductController
import data.spring.mybatis.application.provided.member.MemberUseCase
import data.spring.mybatis.application.provided.product.ProductUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest(
    controllers = [
        ProductController::class,
        MemberController::class
    ]
)
abstract class ControllerTestSupport {
    @Autowired
    protected lateinit var mockMvc: MockMvc

    @MockkBean
    protected lateinit var productUseCase: ProductUseCase

    @MockitoBean
    protected lateinit var memberUseCase: MemberUseCase
}