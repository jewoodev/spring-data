package data.spring.mybatis.domain.product

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class QuantityTest {
    @Test
    fun `quantity is created successfully`() {
        val value = 10
        val quantity = Quantity(value)
        assertThat(quantity.value).isEqualTo(value)
    }

    @Test
    fun `quantity must be non-negative`() {
        assertThatThrownBy { Quantity(-1) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("재고 수량은 0 이상이어야 합니다.")
    }

    @Test
    fun `quantity is increased successfully`() {
        val quantity = Quantity(10)
        val increased = quantity.increase(5)
        assertThat(increased.value).isEqualTo(15)
    }

    @Test
    fun `quantity increase fails with invalid amount`() {
        val quantity = Quantity(10)
        assertThatThrownBy { quantity.increase(0) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("재고 증가량은 양수여야 합니다.")
        
        assertThatThrownBy { quantity.increase(-5) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("재고 증가량은 양수여야 합니다.")
    }

    @Test
    fun `quantity is decreased successfully`() {
        val quantity = Quantity(10)
        val decreased = quantity.decrease(5)
        assertThat(decreased.value).isEqualTo(5)
    }

    @Test
    fun `quantity decrease fails with invalid amount`() {
        val quantity = Quantity(10)
        assertThatThrownBy { quantity.decrease(0) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("재고 감소량은 양수여야 합니다.")
        
        assertThatThrownBy { quantity.decrease(-5) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("재고 감소량은 양수여야 합니다.")
    }

    @Test
    fun `quantity decrease fails when quantity is insufficient`() {
        val quantity = Quantity(10)
        assertThatThrownBy { quantity.decrease(11) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("재고 수량이 부족합니다.")
    }
}
