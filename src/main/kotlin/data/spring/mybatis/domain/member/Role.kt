package data.spring.mybatis.domain.member

enum class Role(
    val key: String,
    val description: String
) {
    UNVERIFIED("ROLE_UNVERIFIED", "미인증자"),
    BUYER("ROLE_BUYER", "구매자"),
    SELLER("ROLE_SELLER", "판매자"),
    ADMIN("ROLE_ADMIN", "관리자")
}