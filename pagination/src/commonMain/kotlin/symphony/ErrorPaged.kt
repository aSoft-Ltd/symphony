package symphony

import kotlinx.serialization.Serializable

@Serializable
data class ErrorPaged(
    val error: String,
    override val syncing: Boolean = false
) : Paged<Nothing> {
    override val items by lazy { Items.empty(0) }
    override val page: Int = 1
    override val pages: Int = 1
}