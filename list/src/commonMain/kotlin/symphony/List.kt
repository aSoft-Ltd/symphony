@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List as KList
import kotlin.js.JsExport

interface List<T> : Pageable<T>, Selectable<T>, ActionableSelection<T> {
    val rows: KList<Row<T>>
    fun manageActions(block: (SelectorBasedActionsManager<T>) -> Unit): List<T>

    fun <R> map(transform: (T) -> R): List<R>
}