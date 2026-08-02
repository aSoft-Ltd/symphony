package symphony

import kotlinx.serialization.Serializable
import kotlin.math.ceil

@Serializable
sealed interface Paged<out T> {
    /**
     * Current page number, starting from 1
     */
    val page: Int

    /**
     * Total number of pages
     */
    val pages: Int get() = ceil(items.total.toFloat() / items.capacity).toInt()

    /**
     * The actual content of the page
     */
    val items: Items<T>

    /**
     * Whether the page is still being synced or not
     */
    val syncing: Boolean
}