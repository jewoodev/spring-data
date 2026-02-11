package data.spring.mybatis.domain.member

@JvmInline
value class Email(
    val value: String
) {
    init {
        require(value.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)$"))) { "이메일 형식이 유효하지 않습니다: $value" }
    }
}
