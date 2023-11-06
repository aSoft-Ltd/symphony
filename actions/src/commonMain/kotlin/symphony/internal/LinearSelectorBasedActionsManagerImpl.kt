package symphony.internal

import symphony.LinearSelected
import symphony.LinearSelectionManager
import symphony.LinearSelectorBasedActionsBuilder

@PublishedApi
internal class LinearSelectorBasedActionsManagerImpl<T>(
    selector: LinearSelectionManager<T>,
    builder: LinearSelectorBasedActionsBuilder<T>
) : AbstractSelectorBasedActionsManager<T,LinearSelected<T>>(selector, builder)