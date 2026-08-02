package symphony

import kotlinx.serialization.Serializable

@Serializable
sealed interface PaginationParams {
    val capacity: Int
    fun toQueryMap(): Map<String, String>

    fun with(capacity: Int = this.capacity): PaginationParams

    companion object {
        fun from(query: String?): PaginationParams? {
            if (query == null) return null
            return from(
                query.substringAfter("?").split("&").associate {
                    val (key, value) = it.split("=")
                    key to value
                }
            )
        }

        fun from(query: Map<String, Any?>): PaginationParams? = when (query[PaginationConstants.Kind.Key]) {
            PaginationConstants.Kind.Value.Cursor -> CursorPaginationParams.from(query)
            PaginationConstants.Kind.Value.Offset -> OffsetPaginationParams.from(query)
            else -> null
        }

        fun first(capacity: Int = 10): PaginationParams = CursorPaginationParams(Cursor.First, capacity)
    }
}