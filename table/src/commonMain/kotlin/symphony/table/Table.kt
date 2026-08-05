@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.table

import kotlinx.JsExport
import symphony.ColumnsManager
import symphony.Paginator
import symphony.Row
import symphony.Selector
import symphony.SelectorBasedActionsManager

interface Table<T> {
    val paginator: Paginator<T>
    val selector: Selector<T>
    val actions: SelectorBasedActionsManager<T>
    val rows: List<Row<T>>
    val columns: ColumnsManager<T>
}