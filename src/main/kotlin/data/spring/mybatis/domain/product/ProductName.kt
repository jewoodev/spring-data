package data.spring.mybatis.domain.product

@JvmInline
value class ProductName(val value: String) {
    init {
        require(value.length in 2..100) { "상품명은 2~100자여야 합니다." }
        require(value.matches(Regex("^[a-zA-Z0-9가-힣\\s]+$"))) { "상품명은 영어, 숫자, 한글, 공백만 허용됩니다." }
    }
}