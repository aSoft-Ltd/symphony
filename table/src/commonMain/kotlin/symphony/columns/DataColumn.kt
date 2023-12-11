@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.columns

import kotlinx.JsExport
import symphony.Row
import symphony.Visibility

data class DataColumn<in D>(
    override val name: String,
    override val key: String,
    override val index: Int,
    override val visibility: Visibility,
    val default: String,
    val accessor: (Row<D>) -> Any?
) : Column<D>() {
    fun resolve(row: Row<D>): String = accessor(row)?.toString() ?: default
}