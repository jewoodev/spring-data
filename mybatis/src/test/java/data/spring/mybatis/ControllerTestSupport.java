package data.spring.mybatis;

import data.spring.mybatis.adapter.in.GlobalControllerAdvice;
import data.spring.mybatis.adapter.in.ProductController;
import data.spring.mybatis.application.required.ProductUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = {
        ProductController.class,
        GlobalControllerAdvice.class
})
public abstract class ControllerTestSupport {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockitoBean
    protected ProductUseCase productUseCase;
}
