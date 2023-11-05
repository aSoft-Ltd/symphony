@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kotlin.js.JsExport

class RemotelyGroupedList<G, T> @PublishedApi internal constructor(
    override val paginator: GroupedPaginationManager<G, T>,
    override val selector: SelectionManager<T>,
    override val actions: SelectorBasedActionsManager<T>
) : GroupedList<G, T>, Selectable<T>, ActionableSelection<T> {
    override val groups: List<Chunk<G, Row<T>>> get() = paginator.continuous
}