@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kotlin.js.JsExport

interface LazyList<T> : Pageable<T>, Selectable<T>, ActionableSelection<T> {
    val rows: List<Row<T>>
    fun manageActions(block: (SelectorBasedActionsManager<T>) -> Unit): LazyList<T>

    fun <R> map(transform: (T) -> R): LazyList<R>
}