package symphony

import kotlinx.serialization.Serializable

@Serializable
data class PagedCursorData<out T>(
    override val params: CursorPaginationParams,
    override val number: PageNumber,
    override val content: List<T>,
    override val capacity: Int,
    override val syncing: Boolean = false,
    val cursor: Cursor
) : PagedData<T> {
    @Serializable
    data class Cursor(
        val next: String?,
        val prev: String?
    ) {
        companion object {
            val Unknown by lazy { Cursor(null, null) }
        }
    }

    companion object {
        fun <T> empty(capacity: Int) = PagedCursorData<T>(
            params = CursorPaginationParams.empty(capacity),
            number = PageNumber.Initial,
            content = emptyList(),
            capacity = capacity,
            syncing = false,
            cursor = Cursor.Unknown
        )

        fun <T> empty(params: PaginationParams) = PagedCursorData<T>(
            params = CursorPaginationParams.empty(params.capacity),
            number = PageNumber.Initial,
            content = emptyList(),
            capacity = params.capacity,
            syncing = false,
            cursor = Cursor.Unknown
        )
    }

    override fun with(capacity: Int) = PagedCursorData(params, number, content, capacity, syncing, cursor)
}