package data.spring.mybatis.domain.product

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PriceTest {
    @Test
    fun createSuccessfully() {
        val amount = 1000
        val price = Price(amount)
        assertThat(price.amount).isEqualTo(amount)
    }

    @Test
    fun createInFailureWithInvalidAmount() {
        assertThatThrownBy { Price(0) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("가격은 1 이상이어야 합니다.")
        
        assertThatThrownBy { Price(-100) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("가격은 1 이상이어야 합니다.")
    }
}
