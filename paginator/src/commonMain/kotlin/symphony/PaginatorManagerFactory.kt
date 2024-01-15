@file:Suppress("FunctionName", "NOTHING_TO_INLINE")

package symphony

import kollections.Collection
import kollections.List
import kollections.chunked
import kollections.emptyList
import kollections.get
import kollections.isEmpty
import kollections.last
import kollections.size
import koncurrent.Later
import symphony.internal.GroupedPaginationManagerImpl
import symphony.internal.LinearPaginationManagerImpl

const val DEFAULT_PAGINATION_CAPACITY = 10

fun <T> Collection<T>.paged(no: Int, capacity: Int): Later<List<T>> {
    val chunked = chunked(capacity)
    val page = when {
        isEmpty() -> emptyList()
        no <= 0 -> chunked.last()
        no <= chunked.size -> chunked[no - 1]
        else -> emptyList()
    }
    return Later(page)
}

inline fun <T> linearPaginatorOf(
    capacity: Int = DEFAULT_PAGINATION_CAPACITY
): LinearPaginationManager<T> = LinearPaginationManagerImpl(capacity)

// Removing this because loading is asynchronous and it is making testing harder
//inline fun <T> linearPaginatorOf(
//    items: Collection<T>,
//    capacity: Int = items.size
//): LinearPaginationManager<T> = LinearPaginationManagerImpl<T>(capacity).also {
//    it.initialize { no, capacity -> items.paged(no, capacity) }
//}

inline fun <G, T> groupedPaginatorOf(
    capacity: Int = DEFAULT_PAGINATION_CAPACITY
): GroupedPaginationManager<G, T> = GroupedPaginationManagerImpl(capacity)

inline fun <T> paginatorOf(
    capacity: Int = DEFAULT_PAGINATION_CAPACITY
): PaginationManager<T, *, *> = linearPaginatorOf(capacity)

//// Removing this because loading is asynchronous and it is making testing harder
//inline fun <T> paginatorOf(
//    items: Collection<T>,
//    capacity: Int = items.size
//): LinearPaginationManager<T> = linearPaginatorOf(items, capacity)