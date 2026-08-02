package symphony

import kotlinx.serialization.Serializable

/**
 * A model representation of what a CursorPaginationParams of data should contain
 * @param cursor the cursor to start from
 * @param capacity the number of items to return
 *
 * e.g.
 * ```markdown
 * CuarsorPaginationParams(cursor=ForwardCursor("uuid-v7-generated"), capacity=10)
 * ```
 */
@Serializable
data class CursorPaginationParams(
    val cursor: Cursor = Cursor.First,
    override val capacity: Int
) : PaginationParams {

    companion object {
        fun empty(capacity: Int) = CursorPaginationParams(Cursor.First, capacity)

        fun from(query: Map<String, Any?>): CursorPaginationParams? {
            if (query[PaginationConstants.Kind.Key] != PaginationConstants.Kind.Value.Cursor) return null
            val capacity = query[PaginationConstants.Capacity]?.toString()?.toIntOrNull() ?: 10
            val reference = query[PaginationConstants.Reference]?.toString() ?: return empty(capacity)
            val direction = when (query[PaginationConstants.Direction.KEY]) {
                PaginationConstants.Direction.Value.Backward -> Cursor.Direction.Backward
                else -> Cursor.Direction.Forward
            }
            return CursorPaginationParams(Cursor(reference, direction), capacity)
        }
    }

    override fun toQueryMap(): Map<String, String> = buildMap {
        putAll(cursor.toQueryMap())
        put(PaginationConstants.Capacity, capacity.toString())
        val reference = cursor.reference ?: return@buildMap
        put(PaginationConstants.Reference, reference)
    }

    override fun <T> with(data: PagedData<T>) = CursorPaginationParams(
        cursor = (data.params as? CursorPaginationParams)?.cursor ?: cursor,
        capacity = data.capacity
    )
}