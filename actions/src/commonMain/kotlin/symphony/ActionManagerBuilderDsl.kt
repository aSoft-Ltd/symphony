@file:Suppress("NOTHING_TO_INLINE")

package symphony

import kevlar.builders.Actions0Builder
import symphony.internal.FixedActionsManagerImpl
import symphony.internal.SelectorBasedActionsManagerImpl

inline fun <T> actionsOf(
    selector: SelectionManager<T>,
    builder: SelectorBasedActionsBuilder<T>.() -> Unit
): SelectorBasedActionsManager<T> = SelectorBasedActionsManagerImpl(selector, SelectorBasedActionsBuilder<T>().apply(builder))

inline fun <T> actionsOf(): SelectorBasedActionsManager<T> = SelectorBasedActionsManagerImpl(
    selector = SelectionManager(SinglePagePaginator()),
    builder = SelectorBasedActionsBuilder()
)

inline fun actionsOf(
    builder: Actions0Builder<Unit>.() -> Unit
): FixedActionsManager = FixedActionsManagerImpl(FixedActionsBuilder().apply { primary(builder) })