package data.spring.mybatis;

import data.spring.mybatis.adapter.in.product.ProductController;
import data.spring.mybatis.application.provided.product.ProductUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        ProductController.class,
})
public abstract class ControllerTestSupport {
    @Autowired
    protected MockMvc mockMvc;

//    @Autowired
//    protected ObjectMapper objectMapper;

    @MockitoBean
    protected ProductUseCase productUseCase;
}
