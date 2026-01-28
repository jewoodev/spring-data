package data.spring.mybatis.adapter.`in`.product

import data.spring.mybatis.adapter.`in`.product.dto.ProductUpdateBatchRequest
import data.spring.mybatis.application.provided.product.ProductUseCase
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/products/v1")
@RestController
class ProductController(
    val productUseCase: ProductUseCase
) {
    @GetMapping("/hello")
    fun hello(): String {
        return "Hello World!"
    }

    @PatchMapping("/update")
    fun updateProducts(@Valid @RequestBody updateBatchRequest: ProductUpdateBatchRequest) {
        this.productUseCase.updateAll(updateBatchRequest.toCommands())
    }
}