@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.JsExport

interface ColumnMover<D> {
    fun before(name: String): ColumnsManager<D>

    fun after(name: String): ColumnsManager<D>
}