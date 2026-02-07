package data.spring.mybatis.adapter.`in`.product.request

import java.time.LocalDateTime
import java.util.Base64

data class CursorInfo(
    val createdAt: LocalDateTime,
    val id: Long
) {
    fun encode(): String {
        return Base64.getEncoder().encodeToString("$createdAt/$id".toByteArray())
    }

    companion object {
        fun decode(encoded: String): CursorInfo {
            val decoded = String(Base64.getDecoder().decode(encoded))
            val (createdAt, id) = decoded.split("/")

            return CursorInfo(
                createdAt = LocalDateTime.parse(createdAt),
                id = id.toLong()
            )
        }
    }
}
