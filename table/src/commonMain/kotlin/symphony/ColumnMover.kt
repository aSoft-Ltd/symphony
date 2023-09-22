@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport
import kotlin.js.JsName

interface ColumnMover<D> {
    fun before(name: String): ColumnsManager<D>

    fun after(name: String): ColumnsManager<D>
}