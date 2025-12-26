@file:Suppress("FunctionName", "NOTHING_TO_INLINE")

package symphony

import symphony.internal.GroupedPaginationManagerImpl
import symphony.internal.LinearPaginationManagerImpl

const val DEFAULT_PAGINATION_CAPACITY = 10

fun <T> Collection<T>.paged(no: Int, capacity: Int): List<T> {
    val chunked = chunked(capacity)
    val page = when {
        isEmpty() -> emptyList()
        no <= 0 -> chunked.last()
        no <= chunked.size -> chunked[no - 1]
        else -> emptyList()
    }
    return page
}

fun <T> Collection<T>.paged(params: PageLoaderParams) = paged(params.page, params.limit)

inline fun <T> linearPaginatorOf(
    capacity: Int = DEFAULT_PAGINATION_CAPACITY,
): LinearPaginationManager<T> = LinearPaginationManagerImpl(capacity)

// Removing this because loading is asynchronous and it is making testing harder
//inline fun <T> linearPaginatorOf(
//    items: Collection<T>,
//    capacity: Int = items.size
//): LinearPaginationManager<T> = LinearPaginationManagerImpl<T>(capacity).also {
//    it.initialize { items.paged(params) }
//}

inline fun <G, T> groupedPaginatorOf(
    capacity: Int = DEFAULT_PAGINATION_CAPACITY,
): GroupedPaginationManager<G, T> = GroupedPaginationManagerImpl(capacity)

inline fun <T> paginatorOf(
    capacity: Int = DEFAULT_PAGINATION_CAPACITY,
): PaginationManager<T, *, *> = linearPaginatorOf(capacity)

//// Removing this because loading is asynchronous and it is making testing harder
//inline fun <T> paginatorOf(
//    items: Collection<T>,
//    capacity: Int = items.size
//): LinearPaginationManager<T> = linearPaginatorOf(items, capacity)