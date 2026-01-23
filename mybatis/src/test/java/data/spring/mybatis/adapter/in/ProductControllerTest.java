package data.spring.mybatis.adapter.in;

import data.spring.mybatis.ControllerTestSupport;
import org.junit.jupiter.api.Test;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest extends ControllerTestSupport {

    // ================================ Validation Test ================================

    @Test
    void updateProductWithNoData() throws Exception {
        // given
        String content = """
                {    
                    "updateRequests": [
                        {
                            "productId": 1,
                            "productName": null,
                            "price": null,
                            "quantity": null
                        }
                    ]
                }""";

        // when, then
        mockMvc.perform(
                        patch("/products/v1/update-all")
                                .content(content)
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("수정할 정보가 없습니다."));
    }

    @Test
    void updateProductWithShortName() throws Exception {
        String content = """
                {    
                    "updateRequests": [
                        {
                            "productId": 1,
                            "productName": "s",
                            "price": null,
                            "quantity": null
                        }
                    ]
                }""";

        // when, then
        mockMvc.perform(
                        patch("/products/v1/update-all")
                                .content(content)
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("상품명은 2자 이상 100자 이하로 입력해주세요."));
    }
}