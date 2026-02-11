package data.spring.mybatis.application.provided.member

import data.spring.mybatis.domain.member.Member
import data.spring.mybatis.application.provided.member.dto.EmailVerifyCommand
import data.spring.mybatis.application.provided.member.dto.MemberCreateCommand
import data.spring.mybatis.application.provided.member.dto.VfcCodeSendCommand

interface MemberUseCase {
    fun register(createCommand: MemberCreateCommand): Int

    fun sendVerificationCode(codeSendCommand: VfcCodeSendCommand)
    fun verify(verifyCommand: EmailVerifyCommand): Int

    fun findById(memberId: Long): Member
    fun findAll(): List<Member>

    fun changePassword(memberId: Long, newPassword: String): Int

    fun leave(memberId: Long):Int

    fun deleteAll(): Int
}