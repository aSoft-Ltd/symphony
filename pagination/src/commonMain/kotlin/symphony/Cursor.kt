package symphony

import kotlinx.serialization.Serializable

@Serializable
data class Cursor(
    val reference: String?,
    val direction: Direction
) {
    enum class Direction {
        Forward, Backward
    }

    companion object {
        val First by lazy { Cursor(null, Direction.Forward) }
        val Last by lazy { Cursor(null, Direction.Backward) }
    }

    fun toQueryMap(): Map<String, String> = buildMap {
        put(PaginationConstants.Kind.Key, PaginationConstants.Kind.Value.Cursor)
        put(PaginationConstants.Direction.KEY, direction.name.lowercase())
        if (reference != null) put(PaginationConstants.Reference, reference)
    }
}