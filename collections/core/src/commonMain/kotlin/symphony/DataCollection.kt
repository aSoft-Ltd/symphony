@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport

interface DataCollection<T> : Pageable<T>, Selectable<T>, Actionable<T> {
    fun manageActions(block: (manager: ActionsManager<T>) -> Unit): DataCollection<T>

    fun <R> map(transform: (T) -> R): DataCollection<R>
}