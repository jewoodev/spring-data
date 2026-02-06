package data.spring.mybatis.domain.product

@JvmInline
value class Quantity(val value: Int) {
    init {
        require(value >= 0) { "재고 수량은 0 이상이어야 합니다." }
    }

    fun increase(amount: Int): Quantity {
        require(amount > 0) { "재고 증가량은 양수여야 합니다." }
        return Quantity(value + amount)
    }

    fun decrease(amount: Int): Quantity {
        require(amount > 0) { "재고 감소량은 양수여야 합니다." }
        require(amount <= value) { "재고 수량이 부족합니다." }
        return Quantity(value - amount)
    }
}
