@file:Suppress("NOTHING_TO_INLINE")

package symphony

import symphony.internal.BluntLinearSelectionManager
import symphony.internal.GroupedSelectionManagerImpl
import symphony.internal.LinearSelectionManagerImpl

inline fun selectorOf(): LinearSelectionManager<Nothing> = BluntLinearSelectionManager.instance

inline fun <T> selectorOf(
    paginator: LinearPaginationManager<T>
): LinearSelectionManager<T> = LinearSelectionManagerImpl(paginator)

inline fun <G, T> selectorOf(
    paginator: GroupedPaginationManager<G, T>
): GroupedSelectionManager<G, T> = GroupedSelectionManagerImpl(paginator)

inline fun <T> selectorOf(
    paginator: PaginationManager<T, *, *>
): SelectionManager<T, *> = when (paginator) {
    is LinearPaginationManager -> selectorOf(paginator)
    is GroupedPaginationManager<*, T> -> selectorOf(paginator)
    else -> throw IllegalArgumentException("Unsupported paginator type: ${paginator::class.simpleName}")
}