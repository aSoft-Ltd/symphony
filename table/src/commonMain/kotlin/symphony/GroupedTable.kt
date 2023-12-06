@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport

interface GroupedTable<T> : Table<T> {
    override val paginator: GroupedPaginationManager<*, T>
    override val selector: GroupedSelectionManager<*, T>
}