@file:Suppress("NOTHING_TO_INLINE")

package symphony

import kevlar.builders.Actions0Builder
import symphony.internal.ActionsManagerImpl
import symphony.internal.FixedActionsManagerImpl

inline fun <T> actionsOf(
    selector: SelectionManager<T>,
    builder: SelectorBasedActionsBuilder<T>.() -> Unit
): ActionsManager<T> = ActionsManagerImpl(selector, SelectorBasedActionsBuilder<T>().apply(builder))

inline fun <T> actionsOf(): ActionsManager<T> = ActionsManagerImpl(
    selector = SelectionManager(SinglePagePaginator()),
    builder = SelectorBasedActionsBuilder()
)

inline fun actionsOf(
    builder: Actions0Builder<Unit>.() -> Unit
): ActionsManager<Any> = FixedActionsManagerImpl(FixedActionsBuilder().apply { primary(builder) })