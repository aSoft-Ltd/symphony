package symphony

import kotlinx.serialization.Serializable

@Serializable
data class PagedOffsetData<out T>(
    override val params: OffsetPaginationParams,
    override val number: PageNumber,
    override val content: List<T>,
    override val capacity: Int,
    override val syncing: Boolean = false,
) : PagedData<T> {
    companion object {
        fun <T> empty(capacity: Int) = PagedOffsetData<T>(
            params = OffsetPaginationParams.start(capacity),
            number = PageNumber.Initial,
            content = emptyList(),
            capacity = capacity,
            syncing = false
        )

        fun <T> empty(params: PaginationParams) = PagedOffsetData<T>(
            params = OffsetPaginationParams.start(params.capacity),
            number = PageNumber.Initial,
            content = emptyList(),
            capacity = params.capacity,
            syncing = false
        )
    }

    override fun with(capacity: Int) = PagedOffsetData(params, number, content, capacity, syncing)
}