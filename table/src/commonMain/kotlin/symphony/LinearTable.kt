@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport

interface LinearTable<T> : Table<T> {
    override val paginator: LinearPaginationManager<T>
    override val selector: LinearSelectionManager<T>
}