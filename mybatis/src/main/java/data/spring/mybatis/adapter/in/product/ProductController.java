package data.spring.mybatis.adapter.in.product;

import data.spring.mybatis.adapter.in.product.dto.ProductUpdateBatchRequest;
import data.spring.mybatis.application.required.product.ProductUseCase;
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

    @PatchMapping("/update")
    public void updateProducts(@Valid @RequestBody ProductUpdateBatchRequest updateBatchRequest) {
        this.productUseCase.updateAll(updateBatchRequest.toCommands());
    }
}
