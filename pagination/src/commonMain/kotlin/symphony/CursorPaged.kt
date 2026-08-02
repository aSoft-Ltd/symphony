package symphony

import kotlinx.serialization.Serializable

@Serializable
data class CursorPaged<out T>(
    override val page: Int,
    override val items: Items<T>,
    override val syncing: Boolean = false,
    val cursor: Cursor
) : Paged<T> {
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
        fun <T> empty(capacity: Int) = CursorPaged<T>(
            page = 1,
            items = Items.empty(capacity),
            syncing = false,
            cursor = Cursor.Unknown
        )
    }
}