@file:Suppress("NOTHING_TO_INLINE")

package symphony

import symphony.internal.ListImpl

inline fun <T> listOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>,
    actionsManager: SelectorBasedActionsManager<T>
): List<T> = ListImpl(paginator, selector, actionsManager)

inline fun <T> listOf(
    paginator: PaginationManager<T>,
    selector: SelectionManager<T>
): List<T> = ListImpl(paginator, selector, actionsOf(selector) {})