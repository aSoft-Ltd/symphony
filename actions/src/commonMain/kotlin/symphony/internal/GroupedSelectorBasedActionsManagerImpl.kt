package symphony.internal

import symphony.GroupedSelected
import symphony.GroupedSelectionManager
import symphony.GroupedSelectorBasedActionsBuilder

@PublishedApi
internal class GroupedSelectorBasedActionsManagerImpl<G, T>(
    selector: GroupedSelectionManager<G, T>,
    builder: GroupedSelectorBasedActionsBuilder<G, T>
) : AbstractSelectorBasedActionsManager<T,GroupedSelected<G,T>>(selector, builder)