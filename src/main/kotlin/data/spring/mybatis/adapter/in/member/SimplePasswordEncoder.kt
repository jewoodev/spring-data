package data.spring.mybatis.adapter.`in`.member

import data.spring.mybatis.domain.member.PasswordEncoder

class SimplePasswordEncoder : PasswordEncoder {
    override fun encode(rawPassword: String): String {
        return rawPassword.reversed()
    }

    override fun matches(rawPassword: String, encodedPassword: String): Boolean {
        return encodedPassword == encode(rawPassword)
    }
}