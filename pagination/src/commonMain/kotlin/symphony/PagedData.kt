package symphony

import kotlinx.serialization.Serializable

@Serializable
sealed interface PagedData<out T> {
    /**
     * The parameters used to fetch this page
     */
    val params: PaginationParams

    /**
     * Page number, starting from 1
     */
    val number: PageNumber

    /**
     * The actual content of the page
     */
    val content: List<T>

    /**
     * The total page capacity
     */
    val capacity: Int

    /**
     * Whether the page is still being synced or not
     */
    val syncing: Boolean

    fun with(capacity: Int = this.capacity): PagedData<T>
}