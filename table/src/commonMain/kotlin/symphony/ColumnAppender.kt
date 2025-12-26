@file:JsExport

package symphony

import kotlinx.JsExport

interface ColumnAppender<out D> {
    suspend fun selectable(name: String = "Select"): ColumnAppender<D>
    suspend fun data(name: String, accessor: (Row<D>) -> Any?): ColumnAppender<D>
    suspend fun actions(name: String = "Actions"): ColumnAppender<D>
}