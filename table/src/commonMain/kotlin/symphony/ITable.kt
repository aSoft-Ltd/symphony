@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kotlin.js.JsExport

interface ITable<T> {
    val paginator: IPaginationManager<T, *, *>
    val selector: ISelectionManager<T, *>
    val actions: SelectorBasedActionsManager<T>
    val rows: List<Row<T>>
    val columns: ColumnsManager<T>
}