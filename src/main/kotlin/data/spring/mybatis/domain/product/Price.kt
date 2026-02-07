package data.spring.mybatis.domain.product

@JvmInline
value class Price(val amount: Int) {
    init {
        require(amount >= 1) { "가격은 1 이상이어야 합니다." }
    }
}
