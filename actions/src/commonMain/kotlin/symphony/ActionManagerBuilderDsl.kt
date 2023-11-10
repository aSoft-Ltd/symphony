@file:Suppress("NOTHING_TO_INLINE")

package symphony

import kevlar.builders.Actions0Builder
import symphony.internal.BluntLinearSelectionManager
import symphony.internal.FixedActionsManagerImpl
import symphony.internal.GroupedSelectorBasedActionsManagerImpl
import symphony.internal.LinearSelectorBasedActionsManagerImpl

inline fun <T> actionsOf(
    selector: LinearSelectionManager<T>,
    builder: LinearSelectorBasedActionsBuilder<T>.() -> Unit
): SelectorBasedActionsManager<T> = LinearSelectorBasedActionsManagerImpl(selector, LinearSelectorBasedActionsBuilder<T>().apply(builder))

inline fun <G, T> actionsOf(
    selector: GroupedSelectionManager<G, T>,
    builder: GroupedSelectorBasedActionsBuilder<G, T>.() -> Unit
): SelectorBasedActionsManager<T> = GroupedSelectorBasedActionsManagerImpl(selector, GroupedSelectorBasedActionsBuilder<G, T>().apply(builder))

inline fun <T> actionsOf(
    selector: SelectionManager<T, *>,
    builder: AbstractSelectorBasedActionsBuilder<T, *>.() -> Unit
): SelectorBasedActionsManager<T> = when(selector) {
    is LinearSelectionManager -> LinearSelectorBasedActionsManagerImpl(selector, LinearSelectorBasedActionsBuilder<T>().apply(builder))
    is GroupedSelectionManager<*,T> -> GroupedSelectorBasedActionsManagerImpl(selector, GroupedSelectorBasedActionsBuilder<Any?, T>().apply(builder))
    else -> throw IllegalArgumentException("${selector::selected} implementation of SelectionManager is not supported")
}

inline fun emptyActions(): SelectorBasedActionsManager<Nothing> = LinearSelectorBasedActionsManagerImpl(
    selector = BluntLinearSelectionManager.instance,
    builder = LinearSelectorBasedActionsBuilder()
)

inline fun actionsOf(
    noinline builder: Actions0Builder<Unit>.() -> Unit
): FixedActionsManager = FixedActionsManagerImpl(FixedActionsBuilder(builder))