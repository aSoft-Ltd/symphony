@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.Set
import cinematic.Live
import kotlinx.JsExport
import symphony.columns.Column

interface ColumnsManager<D> {
    val current: Live<Set<Column<D>>>
    fun reset(): ColumnsManager<D>
    fun all(): Set<Column<D>>
    fun add(name: String, accessor: (Row<D>) -> String): ColumnsManager<D>

    fun find(name: String): Column<D>?
    fun hide(name: String): ColumnsManager<D>
    fun show(name: String): ColumnsManager<D>

    fun toggleVisibility(name: String) : ColumnsManager<D>

    fun remove(name: String) : ColumnsManager<D>

    fun rename(prev: String, curr: String): ColumnsManager<D>
    fun index(name: String, idx: Int): ColumnsManager<D>

    fun move(name: String) : ColumnMover<D>
}