@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport

interface Table<T> : Pageable<T>, Selectable<T>, Actionable<T> {
    val columns: ColumnsManager<T>
    fun manageActions(block: (ActionsManager<T>) -> Unit): Table<T>
    fun manageColumns(block: (manager: ColumnsManager<T>) -> Unit): Table<T>
    fun <R> map(transform: (T) -> R): Table<R>
}