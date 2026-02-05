package data.spring.mybatis.domain.member

import data.spring.mybatis.domain.clock
import data.spring.mybatis.domain.member.Role.UNVERIFIED
import data.spring.mybatis.adapter.`in`.member.request.MemberCreateRequest
import java.time.LocalDateTime

data class Member constructor(
    val memberId: Long? = null,
    val username: Username,
    var password: Password,
    val email: Email,
    val role: Role = UNVERIFIED,
    val createdAt: LocalDateTime = LocalDateTime.now(clock()),
    val updatedAt: LocalDateTime = LocalDateTime.now(clock()),
    val leftAt: LocalDateTime? = null,
) {
    fun activate(): Member {
        return this.let {
            check(role == UNVERIFIED) { "이미 활성화된 회원입니다." }
            this.copy(role = Role.BUYER, updatedAt = LocalDateTime.now(clock()))
        }
    }

    fun changePassword(newPassword: String, passwordEncoder: PasswordEncoder): Member {
        return this.let {
            this.copy(password = Password(passwordEncoder.encode(newPassword)),
                updatedAt = LocalDateTime.now(clock())
            )
        }
    }

    fun leave(): Member {
        return this.let {
            this.copy(leftAt = LocalDateTime.now(clock()),
                updatedAt = LocalDateTime.now(clock())
            )
        }
    }

    companion object {
        fun register(
            username: String, password: String, email: String, passwordEncoder: PasswordEncoder
        ): Member {
            return Member(username = Username(username),
                password = Password(passwordEncoder.encode(password)),
                email = Email(email)
            )
        }
    }
}