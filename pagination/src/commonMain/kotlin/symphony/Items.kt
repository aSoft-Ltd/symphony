package symphony

import kotlinx.serialization.Serializable

@Serializable
data class Items<out T>(
    val content: List<T>,
    val capacity: Int,
    val total: Int
) {
    companion object {
        fun <T> empty(capacity: Int) = Items<T>(emptyList(), capacity, 0)
    }
}