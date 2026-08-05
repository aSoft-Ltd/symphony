package symphony.internal

import symphony.GeneralSelectorBasedActionsBuilder
import symphony.Selector

@PublishedApi
internal class GeneralSelectorBasedActionsManagerImpl<T>(
    selector: Selector<T>,
    builder: GeneralSelectorBasedActionsBuilder<T>
) : AbstractGeneralSelectorBasedActionsManager<T>(selector, builder)