package symphony

import kotlinx.serialization.Serializable

@Serializable
data class OffsetPaginationParams(
    val offset: Int,
    override val capacity: Int
) : PaginationParams {
    companion object {
        fun start(capacity: Int) = OffsetPaginationParams(0, capacity)
        fun from(query: Map<String, Any?>): OffsetPaginationParams? {
            if (query[PaginationConstants.Kind.Key] != PaginationConstants.Kind.Value.Offset) return null
            return OffsetPaginationParams(
                offset = query[PaginationConstants.Reference]?.toString()?.toIntOrNull() ?: 0,
                capacity = query[PaginationConstants.Capacity]?.toString()?.toIntOrNull() ?: 10
            )
        }
    }

    override fun toQueryMap(): Map<String, String> = buildMap {
        put(PaginationConstants.Kind.Key, PaginationConstants.Kind.Value.Offset)
        put(PaginationConstants.Reference, offset.toString())
        put(PaginationConstants.Capacity, capacity.toString())
    }

    override fun with(capacity: Int) = OffsetPaginationParams(offset, capacity)
}