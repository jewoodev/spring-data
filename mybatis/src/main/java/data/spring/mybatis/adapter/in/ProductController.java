package data.spring.mybatis.adapter.in;

import data.spring.mybatis.adapter.in.dto.ProductUpdateBatchRequest;
import data.spring.mybatis.application.required.ProductUseCase;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/products/v1")
@RestController
public class ProductController {
    private final ProductUseCase productUseCase;

    public ProductController(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @PatchMapping("/update-all")
    public void updateProducts(@Valid @RequestBody ProductUpdateBatchRequest updateBatchRequest) {
        this.productUseCase.updateAll(updateBatchRequest);
    }
}
