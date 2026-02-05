package data.spring.mybatis.domain.member

data class Password(
    val value: String
) {
    init {
        validateLength(value)
    }

    private fun validateLength(value: String) {
        require(value.length in  8..100) { "비밀번호는 8자 이상 100자 이하여야 합니다." }
    }
}
