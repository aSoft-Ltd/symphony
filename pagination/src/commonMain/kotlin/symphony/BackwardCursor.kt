package symphony

import kotlinx.serialization.Serializable

@Serializable
data class BackwardCursor(
    override val value: String? = null
) : DirectionalCursor {
    companion object {
        val Last by lazy { BackwardCursor() }
    }

    override fun toQueryMap(): Map<String, String> = buildMap {
        put(PaginationConstants.Kind.Key, PaginationConstants.Kind.Value.Cursor)
        put(PaginationConstants.Direction.KEY, PaginationConstants.Direction.Value.Backward)
        if (value != null) put(PaginationConstants.Reference, value)
    }
}