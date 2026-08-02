package symphony

import kotlinx.serialization.Serializable

@Serializable
data class OffsetPaged<out T>(
    override val page: Int,
    override val items: Items<T>,
    override val syncing: Boolean = false,
) : Paged<T>