@file:Suppress("NOTHING_TO_INLINE")

package symphony

import kollections.List
import kollections.toIList
import symphony.internal.GroupedListImpl
import symphony.internal.LazyListImpl

@Deprecated("in favour of lazyListOf", replaceWith = ReplaceWith("lazyListOf(paginator,selector,actionsManager)"))
inline fun <T> listOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actionsManager: SelectorBasedActionsManager<T>
): LazyList<T> = LazyListImpl(paginator, selector, actionsManager)

inline fun <T> lazyListOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actionsManager: SelectorBasedActionsManager<T>
): LazyList<T> = LazyListImpl(paginator, selector, actionsManager)

inline fun <G, T> groupedListOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actionsManager: SelectorBasedActionsManager<T>,
    noinline grouper: (List<T>) -> List<Chunk<G, T>>
): GroupedList<G, T> = GroupedListImpl(paginator, grouper, selector, actionsManager)

@Deprecated("in favour of lazyListOf", replaceWith = ReplaceWith("lazyListOf(paginator,selector)"))
inline fun <T> listOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>
): LazyList<T> = LazyListImpl(paginator, selector, actionsOf(selector) {})

inline fun <T> lazyListOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>
): LazyList<T> = LazyListImpl(paginator, selector, actionsOf(selector) {})

inline fun <G, T> groupedListOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    noinline grouper: (T) -> Pair<T, G>
): GroupedList<G, T> {
    val gp: (List<T>) -> List<Chunk<G, T>> = { items ->
        val groups = mutableMapOf<G, MutableList<T>>()
        items.forEach { item ->
            groups.getOrPut(grouper(item).second) { mutableListOf() }.add(item)
        }
        groups.entries.map { (group, items) ->
            Chunk(group, items.mapIndexed { idx, item -> Row(idx, item) }.toIList())
        }.toIList()
    }
    return GroupedListImpl(paginator, gp, selector, actionsOf(selector) { })
}