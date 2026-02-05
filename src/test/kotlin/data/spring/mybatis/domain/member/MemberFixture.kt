package data.spring.mybatis.domain.member

import data.spring.mybatis.adapter.`in`.member.SimplePasswordEncoder
import data.spring.mybatis.adapter.`in`.member.request.MemberCreateRequest

class MemberFixture {
    companion object {
        fun createMember(): Member {
            return createMember("testPassword")
        }

        fun createMember(password: String): Member {
            return createMemberCreateRequest()
                .let { Member.register(it.username, it.password, it.email, createPasswordEncoder()) }
        }

        fun createMemberCreateRequest(): MemberCreateRequest {
            return createMemberCreateRequest("testPassword")
        }

        fun createMemberCreateRequest(password: String): MemberCreateRequest {
            return MemberCreateRequest(
                username = "testuser",
                email = "test@example.com",
                password = password
            )
        }

        fun createPasswordEncoder(): PasswordEncoder {
            return SimplePasswordEncoder()
        }
    }
}