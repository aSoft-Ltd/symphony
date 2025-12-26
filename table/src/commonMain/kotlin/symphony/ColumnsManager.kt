@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.Live
import kotlinx.JsExport
import symphony.columns.Column

interface ColumnsManager<D> {
    val current: Live<Set<Column<D>>>

    fun reset(): ColumnsManager<D>

    suspend fun initialize(): ColumnsManager<D>

    fun all(): Set<Column<D>>

    /**
     * Adds a column
     *
     * NOTE: Adding a column with the same name will not do anything
     */
    @Deprecated("in favor of append.data", replaceWith = ReplaceWith("append.data(name, accessor)"))
    suspend fun add(name: String, accessor: (Row<D>) -> Any?): ColumnsManager<D>

    val append: ColumnAppender<D>

    fun find(name: String): Column<D>?

    suspend fun hide(name: String): ColumnsManager<D>

    suspend fun show(name: String): ColumnsManager<D>

    suspend fun toggleVisibility(name: String): ColumnsManager<D>

    suspend fun remove(name: String): ColumnsManager<D>

    suspend fun rename(prev: String, curr: String): ColumnsManager<D>

    fun move(name: String): Mover
}
