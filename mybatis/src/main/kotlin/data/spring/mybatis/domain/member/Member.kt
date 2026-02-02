package data.spring.mybatis.domain.member

import data.spring.mybatis.domain.clock
import data.spring.mybatis.domain.member.Role.UNVERIFIED
import data.spring.mybatis.domain.member.request.MemberCreateRequest
import org.springframework.util.Assert.state
import java.time.LocalDateTime

data class Member constructor(
    val memberId: Long? = null,
    val username: String,
    var password: String,
    val email: String,
    var role: Role = UNVERIFIED,
    val createdAt: LocalDateTime = LocalDateTime.now(clock()),
    var updatedAt: LocalDateTime = LocalDateTime.now(clock()),
    var leftAt: LocalDateTime? = null,
) {
    companion object {
        fun register(
            createRequest: MemberCreateRequest, passwordEncoder: PasswordEncoder
        ): Member {
            return passwordEncoder.encode(createRequest.password)
                .let { Member(username = createRequest.username, password = it, email = createRequest.email) }
        }
    }

    fun activate(): Member {
        state(role == UNVERIFIED, "이미 활성화된 회원입니다.")
        this.role = Role.BUYER
        return this
    }

    fun changePassword(newPassword: String, passwordEncoder: PasswordEncoder): Member {
        this.password = passwordEncoder.encode(newPassword)
        return this
    }

    fun leave(): Member {
        this.leftAt = LocalDateTime.now(clock())
        return this
    }
}