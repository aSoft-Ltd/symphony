@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")
package symphony

import cinematic.MutableLive
import cinematic.mutableLiveOf
import kotlinx.JsExport

class ColumnSorter(
    val onSort:(data:ColumnSort)->Unit
) {
    val data: MutableLive<ColumnSort?> = mutableLiveOf(null)

    fun toggle(column:String) {
        val sort = ColumnSort(
            by = column,
            order = data.value?.order?.reversed ?: ColumnSortOrder.dsc
        )
        data.value = sort
        onSort(sort)
    }
}