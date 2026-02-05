package data.spring.mybatis.domain.member

import java.util.regex.Pattern

data class Email(
    val value: String
) {
    init {
        validateFormat(value)
    }

    private fun validateFormat(value: String) {
        value.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)$"))
            .let { require(it) { "이메일 형식이 유효하지 않습니다: $value" } }
    }
}
