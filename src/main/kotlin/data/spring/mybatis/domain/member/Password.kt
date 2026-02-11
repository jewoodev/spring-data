package data.spring.mybatis.domain.member

@JvmInline
value class Password(
    val value: String
) {
    init {
        require(value.length in  11..100) { "비밀번호는 11자 이상 100자 이하여야 합니다." }
    }
}
