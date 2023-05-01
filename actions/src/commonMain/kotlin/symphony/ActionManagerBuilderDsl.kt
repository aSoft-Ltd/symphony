@file:Suppress("NOTHING_TO_INLINE")

package symphony

import symphony.internal.ActionsManagerImpl

inline fun <T> actionsOf(
    selector: SelectionManager<T>,
    builder: CollectionActionsBuilder<T>.() -> Unit
): ActionsManager<T> = ActionsManagerImpl(selector, CollectionActionsBuilder<T>().apply(builder))

inline fun <T> actionsOf(): ActionsManager<T> = ActionsManagerImpl(
    selector = SelectionManager(SinglePagePaginator()),
    builder = CollectionActionsBuilder()
)