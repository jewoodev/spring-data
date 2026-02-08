package data.spring.mybatis.domain.member

fun interface MemberDuplicationVerifier {
    fun verify(username: String, emailAddr: String): Boolean
}