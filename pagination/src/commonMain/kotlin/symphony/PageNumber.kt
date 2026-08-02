package symphony

import kotlinx.serialization.Serializable

@Serializable
data class PageNumber(
    val current: Int,
    val total: Int
) {
    companion object {
        val Initial by lazy { PageNumber(1, 1) }
    }
}