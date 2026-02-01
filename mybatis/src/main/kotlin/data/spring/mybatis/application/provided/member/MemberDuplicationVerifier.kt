package data.spring.mybatis.application.provided.member

interface MemberDuplicationVerifier {
    fun verify(username: String, emailAddr: String)
}