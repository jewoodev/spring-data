package data.spring.mybatis.domain.member

import data.spring.mybatis.domain.BaseDomain
import data.spring.mybatis.domain.member.Role.UNVERIFIED
import data.spring.mybatis.domain.member.request.MemberCreateRequest
import org.springframework.util.Assert.state

data class Member constructor(
    val memberId: Long? = null,
    val username: String,
    var password: String,
    val email: String,
    var role: Role = UNVERIFIED
) : BaseDomain() {
    companion object {
        fun register(
            createRequest: MemberCreateRequest, passwordEncoder: PasswordEncoder
        ): Member {
            val encodedPassword = passwordEncoder.encode(createRequest.password)
            return Member(username = createRequest.username, password = encodedPassword, email = createRequest.email)
        }
    }

    fun activate(): Member {
        state(role == UNVERIFIED, "이미 활성화된 회원입니다.")
        this.role = Role.BUYER
        return this
    }
}