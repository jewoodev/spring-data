package data.spring.mybatis.domain.member

import data.spring.mybatis.adapter.`in`.member.SimplePasswordEncoder
import data.spring.mybatis.domain.member.request.MemberCreateRequest

class MemberFixture {
    companion object {
        fun createMember(password: String): Member {
            return Member.register(MemberCreateRequest(
                username = "testuser",
                email = "test@example.com",
                password = password
            ), createPasswordEncoder())
        }

        fun createPasswordEncoder(): PasswordEncoder {
            return SimplePasswordEncoder()
        }
    }
}