package data.spring.mybatis.adapter.`in`.product

import data.spring.mybatis.adapter.`in`.product.request.CursorInfo
import data.spring.mybatis.adapter.`in`.product.request.ProductCreateRequest
import data.spring.mybatis.adapter.`in`.product.request.ProductUpdateBatchRequest
import data.spring.mybatis.adapter.`in`.product.response.ProductResponse
import data.spring.mybatis.adapter.`in`.response.CursorPageResponse
import data.spring.mybatis.application.provided.product.ProductUseCase
import data.spring.mybatis.application.provided.product.dto.ProductCreateCommand
import data.spring.mybatis.application.provided.product.dto.ProductSearchCond
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RequestMapping("/products/v1")
@RestController
class ProductController(
    val productUseCase: ProductUseCase
) {
    @GetMapping("/list")
    fun getProductsWithCond(
        @RequestParam(required = false) productName: String?,
        @RequestParam(required = false) maxPrice: Int?,
        @RequestParam(required = false) cursor: String?, // encoded cursor
        @RequestParam(defaultValue = "20") size: Int
    ): CursorPageResponse<ProductResponse> {
        if (size !in 10..100) throw PageSizeException("size는 10에서 100 사이여야 합니다: $size")

        val cursorInfo = cursor?.let{ CursorInfo.decode(it) }
        val products = productUseCase.findByCond(
            searchCond = if (productName == null && maxPrice == null) null else ProductSearchCond(productName, maxPrice),
            createdAt = cursorInfo?.createdAt,
            productId = cursorInfo?.id,
            size = size + 1
        )

        val hasNext = products.size > size
        val content = if (hasNext) products.dropLast(1) else products
        val nextCursor = if (hasNext) {
            val lastProduct = content.last()
            CursorInfo(
                createdAt = lastProduct.createdAt,
                id = lastProduct.productId!!
            ).encode()
        } else null

        return CursorPageResponse(
            content = content.map { ProductResponse.fromDomain(it) },
            nextCursor = nextCursor,
            hasNext = hasNext
        )
    }

    @PostMapping("/save")
    fun createProduct(@Valid @RequestBody createRequest: ProductCreateRequest): ResponseEntity<String> {
        productUseCase.save(createRequest.toCommand())
        return ResponseEntity.created(URI.create("products/v1/list")).body("상품 저장에 성공했습니다.")
    }

    @PatchMapping("/update")
    fun updateProducts(@Valid @RequestBody updateBatchRequest: ProductUpdateBatchRequest) {
        updateBatchRequest.toCommands().forEach { this.productUseCase.update(it) }
    }
}