@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.internal

import kollections.List
import symphony.ActionableSelection
import symphony.Chunk
import symphony.GroupedList
import symphony.GroupedPaginationManager
import symphony.GroupedSelectionManager
import symphony.Row
import symphony.SelectorBasedActionsManager

@PublishedApi
internal class RemotelyGroupedList<G, T> @PublishedApi internal constructor(
    override val paginator: GroupedPaginationManager<G, T>,
    override val selector: GroupedSelectionManager<G, T>,
    override val actions: SelectorBasedActionsManager<T>
) : GroupedList<G, T>, ActionableSelection<T> {
    override val groups: List<Chunk<G, Row<T>>> get() = paginator.continuous
}