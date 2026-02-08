package data.spring.mybatis.domain.member

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class UsernameTest {
    @Test
    fun `create username successfully`() {
        val validUsername = "user123"
        val username = Username(validUsername)
        assertThat(username.value).isEqualTo(validUsername)
    }

    @ParameterizedTest
    @ValueSource(strings = ["a", "this_is_a_very_long_username_that_exceeds_twenty_characters_limit_check_required"])
    fun `throw exception when username length is invalid`(invalidValue: String) {
        assertThatThrownBy { Username(invalidValue) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

    @ParameterizedTest
    @ValueSource(strings = ["user!", "user space", "한글사용자"])
    fun `throw exception when username format is invalid`(invalidValue: String) {
        assertThatThrownBy { Username(invalidValue) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("아이디는 알파벳, 숫자, 언더스코어, 하이픈만 포함할 수 있습니다.")
    }
}
