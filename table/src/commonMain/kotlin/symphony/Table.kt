@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.JsExport

interface Table<T> {
    val paginator: PaginationManager<T, *, *>
    val selector: SelectionManager<T, *>
    val actions: SelectorBasedActionsManager<T>
    val rows: List<Row<T>>
    val columns: ColumnsManager<T>
}