@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kotlin.js.JsExport

interface GroupedList<out G, out T> {
    val paginator: IPaginationManager<T, *, *>
    val selector: Any
    val actions: SelectorBasedActionsManager<T>
    val groups: List<Chunk<G, Row<T>>>
}