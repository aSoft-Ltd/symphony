@file:Suppress("FunctionName", "NOTHING_TO_INLINE")

package symphony

import koncurrent.Later
import koncurrent.SynchronousExecutor
import symphony.internal.PaginationManagerImpl

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