package data.spring.mybatis.domain.product

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ProductNameTest {
    @ParameterizedTest
    @ValueSource(strings = ["Test Product", "상품명 123", "한글", "English", "12345"])
    fun `product name is created successfully with allowed characters`(validName: String) {
        val productName = ProductName(validName)
        assertThat(productName.value).isEqualTo(validName)
    }

    @Test
    fun `product name creation fails with invalid length`() {
        assertThatThrownBy { ProductName("A") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("상품명은 2~100자여야 합니다.")

        val tooLongName = "a".repeat(101)
        assertThatThrownBy { ProductName(tooLongName) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("상품명은 2~100자여야 합니다.")
    }

    @ParameterizedTest
    @ValueSource(strings = ["Test!", "상품@", "123#"])
    fun `product name creation fails with characters that are not allowed`(invalidName: String) {
        assertThatThrownBy { ProductName(invalidName) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("상품명은 영어, 숫자, 한글, 공백만 허용됩니다.")
    }
}
