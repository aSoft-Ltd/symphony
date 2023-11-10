@file:Suppress("NOTHING_TO_INLINE")

package symphony

import symphony.internal.LinearlyPaginatedLinearList
import symphony.internal.LinearlyPaginatedGroupedList
import symphony.internal.GroupedPaginatedGroupedList
import symphony.internal.GroupedPaginatedLinearList

inline fun <T> lazyListOf(
    paginator: LinearPaginationManager<T>
): LinearList<T> = LinearlyPaginatedLinearList(paginator)


inline fun <G, T> lazyListOf(
    paginator: LinearPaginationManager<T>,
    noinline grouper: (T) -> Pair<T, G>
): GroupedList<G, T> = LinearlyPaginatedGroupedList(paginator, Grouper(grouper))

inline fun <G, T> lazyListOf(
    paginator: GroupedPaginationManager<G, T>
): GroupedList<G, T> = GroupedPaginatedGroupedList(paginator)


inline fun <T> lazyListOf(
    paginator: PaginationManager<T, *, *>
): LazyList<T> = when (paginator) {
    is LinearPaginationManager -> lazyListOf(paginator)
    is GroupedPaginationManager<*, T> -> lazyListOf(paginator)
    else -> throw IllegalArgumentException("implementation ${paginator::class.simpleName} of PaginationManager is not supported")
}

inline fun <G, T> lazyListOf(
    paginator: PaginationManager<T, *, *>,
    noinline grouper: (T) -> Pair<T, G>
): GroupedList<G, T> = when (paginator) {
    is LinearPaginationManager -> lazyListOf(paginator, grouper)
    is GroupedPaginationManager<*, *> -> lazyListOf(paginator as GroupedPaginationManager<G, T>)
    else -> throw IllegalArgumentException("implementation ${paginator::class.simpleName} of PaginationManager is not supported")
}

inline fun <T> linearListOf(
    paginator: GroupedPaginationManager<*,T>
): LinearList<T> = GroupedPaginatedLinearList(paginator)

inline fun <T> linearListOf(
    paginator: PaginationManager<T,*,*>
): LinearList<T> = when (paginator) {
    is LinearPaginationManager -> lazyListOf(paginator)
    is GroupedPaginationManager<*, T> -> linearListOf(paginator)
    else -> throw IllegalArgumentException("implementation ${paginator::class.simpleName} of PaginationManager is not supported")
}