package data.spring.mybatis.adapter.`in`.member.request

import data.spring.mybatis.application.provided.member.dto.VfcCodeSendCommand


import jakarta.validation.constraints.NotNull

data class VfcCodeSendRequest(
    @field:NotNull
    val memberId: Long
) {
    fun toCommand(): VfcCodeSendCommand = VfcCodeSendCommand(memberId)
}