@file:Suppress("NOTHING_TO_INLINE")

package symphony

@Deprecated("in favour of lazyListOf", replaceWith = ReplaceWith("lazyListOf(paginator,selector,actionsManager)"))
inline fun <T> listOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actionsManager: SelectorBasedActionsManager<T>
): LazyList<T> = LazyList(paginator, selector, actionsManager)

inline fun <T> lazyListOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actionsManager: SelectorBasedActionsManager<T>
): LazyList<T> = LazyList(paginator, selector, actionsManager)

@Deprecated("in favour of lazyListOf", replaceWith = ReplaceWith("lazyListOf(paginator,selector)"))
inline fun <T> listOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>
): LazyList<T> = LazyList(paginator, selector, actionsOf(selector) {})

inline fun <T> lazyListOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>
): LazyList<T> = LazyList(paginator, selector, actionsOf(selector) {})


inline fun <G, T> locallyGroupedListOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actionsManager: SelectorBasedActionsManager<T>,
    noinline grouper: (T) -> Pair<T, G>
): LocallyGroupedList<G, T> = LocallyGroupedList(paginator, Grouper(grouper), selector, actionsManager)

inline fun <G, T> locallyGroupedListOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    noinline grouper: (T) -> Pair<T, G>
): LocallyGroupedList<G, T> = LocallyGroupedList(paginator, Grouper(grouper), selector, actionsOf(selector) { })

