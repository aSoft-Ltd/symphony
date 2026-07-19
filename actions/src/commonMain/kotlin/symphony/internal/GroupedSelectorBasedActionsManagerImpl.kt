package symphony.internal

import symphony.selected.GroupedSelected
import symphony.GroupedSelectionManager
import symphony.GroupedSelectorBasedActionsBuilder
import symphony.LinearSelectorBasedActionsBuilder

@PublishedApi
internal class GroupedSelectorBasedActionsManagerImpl<G, T>(
    selector: GroupedSelectionManager<G, T>,
    builder: GroupedSelectorBasedActionsBuilder<G, T>
) : AbstractSelectorBasedActionsManager<T, GroupedSelected<G, T>>(selector, builder) {
    override fun redefine(body: LinearSelectorBasedActionsBuilder<T>.() -> Unit) {
        println("Can not yet redefin group selector based actions")
    }
}