@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")
package symphony

import cinematic.MutableLive
import cinematic.mutableLiveOf
import kotlinx.JsExport

data class ColumnSort(
    val by:String,
    val order:ColumnSortOrder
) {
}