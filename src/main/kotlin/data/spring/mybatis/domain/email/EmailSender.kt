package data.spring.mybatis.domain.email

import data.spring.mybatis.domain.member.Email

interface EmailSender {
    fun sendVerificationCode(email: Email)
}