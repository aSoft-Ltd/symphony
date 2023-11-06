@file:Suppress("FunctionName", "NOTHING_TO_INLINE")

package symphony

import koncurrent.Later
import symphony.internal.GroupedPaginationManagerImpl
import symphony.internal.LinearPaginationManagerImpl
import symphony.internal.PaginationManagerImpl

const val DEFAULT_PAGINATION_CAPACITY = 10
inline fun <T> PaginationManager(
    capacity: Int = PaginationManagerImpl.DEFAULT_CAPACITY,
    noinline loader: PageLoader<T>? = null
): PaginationManager<T> = PaginationManagerImpl(capacity = capacity, loader = loader)

inline fun <T> SinglePagePaginator(
    page: Page<T> = Page()
): PaginationManager<T> = SinglePagePaginator(page.items.map { it.item })

inline fun <T> SinglePagePaginator(
    items: Collection<T>,
): PaginationManager<T> = PaginationManagerImpl(capacity = items.size) { _, _ ->
    Later(items)
}

fun <T> Collection<T>.paged(no: Int, capacity: Int): Later<List<T>> {
    val chunked = chunked(capacity)
    val page = when {
        isEmpty() -> toList()
        no <= 0 -> chunked.last()
        no <= chunked.size -> chunked[no - 1]
        else -> emptyList()
    }
    return Later(page)
}

fun <T> CollectionPaginator(
    collection: Collection<T>,
    capacity: Int = PaginationManagerImpl.DEFAULT_CAPACITY,
): PaginationManager<T> = PaginationManager(capacity) { no, cap -> collection.paged(no, cap) }

inline fun <T> PageLoader(noinline loader: PageLoader<T>) = loader

internal fun <T> linearPaginatorOf(
    capacity: Int = DEFAULT_PAGINATION_CAPACITY
): LinearPaginationManager<T> = LinearPaginationManagerImpl(capacity)

inline fun <T> linearPaginatorOf(
    items: Collection<T>,
    capacity: Int = items.size
): LinearPaginationManager<T> = LinearPaginationManagerImpl<T>(capacity).also {
    it.initialize { no, capacity -> items.paged(no, capacity) }
}

internal fun <G, T> groupedPaginatorOf(
    capacity: Int = DEFAULT_PAGINATION_CAPACITY
): GroupedPaginationManager<G, T> = GroupedPaginationManagerImpl(capacity)