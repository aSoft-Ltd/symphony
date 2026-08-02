package symphony

import kotlinx.serialization.Serializable

@Serializable
data class ForwardCursor(
    override val value: String? = null
) : DirectionalCursor {
    companion object {
        val First by lazy { ForwardCursor() }
    }

    override fun toQueryMap(): Map<String, String> = buildMap {
        put(PaginationConstants.Kind.Key, PaginationConstants.Kind.Value.Cursor)
        put(PaginationConstants.Direction.KEY, PaginationConstants.Direction.Value.Forward)
        if (value != null) put(PaginationConstants.Reference, value)
    }
}