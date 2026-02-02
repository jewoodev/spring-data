package data.spring.mybatis.application.provided.member

import data.spring.mybatis.application.provided.member.request.EmailVerifyRequest
import data.spring.mybatis.domain.member.Member
import data.spring.mybatis.domain.member.request.MemberCreateRequest
import data.spring.mybatis.domain.member.request.VfcCodeSendRequest

interface MemberUseCase {
    fun register(createRequest: MemberCreateRequest)

    fun sendVerificationCode(codeSendRequest: VfcCodeSendRequest)
    fun verify(verifyRequest: EmailVerifyRequest)

    fun findById(memberId: Long): Member?
    fun findAll(): List<Member?>

    fun changePassword(member: Member, newPassword: String)

    fun leave(member: Member)

    fun deleteAll(): Int
}