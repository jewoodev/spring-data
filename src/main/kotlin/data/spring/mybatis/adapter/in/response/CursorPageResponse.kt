package data.spring.mybatis.adapter.`in`.response

data class CursorPageResponse<T>(
    val content: List<T>,
    val nextCursor: String?,
    val hasNext: Boolean
)