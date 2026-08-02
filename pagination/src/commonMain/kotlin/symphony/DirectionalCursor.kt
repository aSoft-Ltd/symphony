package symphony

import kotlinx.serialization.Serializable

@Serializable
sealed interface DirectionalCursor {
    val value: String?
    fun toQueryMap(): Map<String, String>
}