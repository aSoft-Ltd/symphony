@file:Suppress("NOTHING_TO_INLINE")

package symphony

import kevlar.builders.Actions0Builder
import symphony.internal.BluntLinearSelectionManager
import symphony.internal.FixedActionsManagerImpl
import symphony.internal.GroupedSelectorBasedActionsManagerImpl
import symphony.internal.LinearSelectorBasedActionsManagerImpl
import symphony.internal.SelectorBasedActionsManagerImpl

@Deprecated("in favour of actionsOf")
inline fun <T> actionsOf(
    selector: SelectionManager<T>,
    builder: SelectorBasedActionsBuilder<T>.() -> Unit
): SelectorBasedActionsManager<T> = SelectorBasedActionsManagerImpl(selector, SelectorBasedActionsBuilder<T>().apply(builder))

@Deprecated("In favour of empthActionsOf")
inline fun <T> actionsOf(): SelectorBasedActionsManager<T> = SelectorBasedActionsManagerImpl(
    selector = SelectionManager(SinglePagePaginator()),
    builder = SelectorBasedActionsBuilder()
)

inline fun <T> actionsOf(
    selector: LinearSelectionManager<T>,
    builder: LinearSelectorBasedActionsBuilder<T>.() -> Unit
): SelectorBasedActionsManager<T> = LinearSelectorBasedActionsManagerImpl(selector, LinearSelectorBasedActionsBuilder<T>().apply(builder))

inline fun <G, T> actionsOf(
    selector: GroupedSelectionManager<G, T>,
    builder: GroupedSelectorBasedActionsBuilder<G, T>.() -> Unit
): SelectorBasedActionsManager<T> = GroupedSelectorBasedActionsManagerImpl(selector, GroupedSelectorBasedActionsBuilder<G, T>().apply(builder))

inline fun emptyActions(): SelectorBasedActionsManager<Nothing> = LinearSelectorBasedActionsManagerImpl(
    selector = BluntLinearSelectionManager.instance,
    builder = LinearSelectorBasedActionsBuilder()
)

inline fun actionsOf(
    noinline builder: Actions0Builder<Unit>.() -> Unit
): FixedActionsManager = FixedActionsManagerImpl(FixedActionsBuilder(builder))