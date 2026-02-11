package data.spring.mybatis.domain.member

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class EmailTest {
    @Test
    fun `create email successfully`() {
        val validEmail = "test@example.com"
        val email = Email(validEmail)
        assertThat(email.value).isEqualTo(validEmail)
    }

    @ParameterizedTest
    @ValueSource(strings = ["plainaddress", "#@%^%#$@#$@#.com", "@example.com", "Joe Smith <email@example.com>", "email.example.com"])
    fun `throw exception when email format is invalid`(invalidValue: String) {
        assertThatThrownBy { Email(invalidValue) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("이메일 형식이 유효하지 않습니다")
    }
}
