@file:JsExport

package symphony

import koncurrent.Later
import kotlinx.JsExport

interface ColumnAppender<out D> {
    fun selectable(name: String = "Select"): Later<ColumnAppender<D>>
    fun data(name: String, accessor: (Row<D>) -> Any?): Later<ColumnAppender<D>>
    fun actions(name: String = "Actions"): Later<ColumnAppender<D>>
}