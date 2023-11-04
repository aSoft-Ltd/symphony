@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kotlin.js.JsExport

interface GroupedList<G, T> : Pageable<T>, Selectable<T>, ActionableSelection<T> {
    val groups: List<Chunk<G, T>>
    fun manageActions(block: (SelectorBasedActionsManager<T>) -> Unit): GroupedList<G, T>
}