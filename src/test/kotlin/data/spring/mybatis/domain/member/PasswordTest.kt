package data.spring.mybatis.domain.member

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PasswordTest {
    @Test
    fun `create password successfully`() {
        val validValue = "password123"
        val password = Password(validValue)
        assertThat(password.value).isEqualTo(validValue)
    }

    @ParameterizedTest
    @ValueSource(strings = ["short", "very_short"])
    fun `throw exception when password length is less than 8`(tooShort: String) {
        assertThatThrownBy { Password(tooShort) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("비밀번호는 11자 이상 100자 이하여야 합니다.")
    }

    @Test
    fun `throw exception when password length exceeds 100`() {
        val tooLong = "a".repeat(101)
        assertThatThrownBy { Password(tooLong) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("비밀번호는 11자 이상 100자 이하여야 합니다.")
    }
}
