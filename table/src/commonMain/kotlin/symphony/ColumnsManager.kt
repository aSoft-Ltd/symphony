@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.Set
import cinematic.Live
import koncurrent.Later
import kotlinx.JsExport
import symphony.columns.Column

interface ColumnsManager<D> {
    val current: Live<Set<Column<D>>>

    fun reset(): ColumnsManager<D>

    fun initialize(): Later<ColumnsManager<D>>

    fun all(): Set<Column<D>>

    /**
     * Adds a column
     *
     * NOTE: Adding a column with the same name will not do anything
     */
    @Deprecated("in favor of append.data", replaceWith = ReplaceWith("append.data(name, accessor)"))
    fun add(name: String, accessor: (Row<D>) -> Any?): Later<ColumnsManager<D>>

    val append: ColumnAppender<D>

    fun find(name: String): Column<D>?

    fun hide(name: String): Later<ColumnsManager<D>>

    fun show(name: String): Later<ColumnsManager<D>>

    fun toggleVisibility(name: String): Later<ColumnsManager<D>>

    fun remove(name: String): Later<ColumnsManager<D>>

    fun rename(prev: String, curr: String): Later<ColumnsManager<D>>

    fun move(name: String): Mover
}
