@file:Suppress("NOTHING_TO_INLINE")

package symphony

import symphony.internal.LocallyGroupedList
import symphony.internal.RemotelyGroupedList

@Deprecated("in favour of lazyListOf", replaceWith = ReplaceWith("lazyListOf(paginator,selector,actionsManager)"))
inline fun <T> listOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actionsManager: SelectorBasedActionsManager<T>
): List<T> = List(paginator, selector, actionsManager)

inline fun <T> lazyListOf(
    paginator: LinearPaginationManager<T>,
    selector: LinearSelectionManager<T>,
    actionsManager: SelectorBasedActionsManager<T>
): LinearList<T> = LinearList(paginator, selector, actionsManager)

@Deprecated("in favour of lazyListOf", replaceWith = ReplaceWith("lazyListOf(paginator,selector)"))
inline fun <T> listOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>
): List<T> = List(paginator, selector, actionsOf(selector) {})

inline fun <T> lazyListOf(
    paginator: LinearPaginationManager<T>,
    selector: LinearSelectionManager<T>
): LinearList<T> = LinearList(paginator, selector, emptyActions())


inline fun <G, T> lazyListOf(
    paginator: LinearPaginationManager<T>,
    selector: LinearSelectionManager<T>,
    noinline grouper: (T) -> Pair<T, G>,
    actionsManager: SelectorBasedActionsManager<T>
): GroupedList<G, T> = LocallyGroupedList(paginator, Grouper(grouper), selector, actionsManager)

inline fun <G, T> lazyListOf(
    paginator: LinearPaginationManager<T>,
    selector: LinearSelectionManager<T>,
    noinline grouper: (T) -> Pair<T, G>
): GroupedList<G, T> = LocallyGroupedList(paginator, Grouper(grouper), selector, emptyActions())

inline fun <G, T> lazyListOf(
    paginator: GroupedPaginationManager<G, T>,
    selector: GroupedSelectionManager<G, T>,
    actionsManager: SelectorBasedActionsManager<T>
): GroupedList<G, T> = RemotelyGroupedList(paginator, selector, actionsManager)

inline fun <G, T> lazyListOf(
    paginator: GroupedPaginationManager<G, T>,
    selector: GroupedSelectionManager<G, T>
): GroupedList<G, T> = RemotelyGroupedList(paginator, selector, emptyActions())
