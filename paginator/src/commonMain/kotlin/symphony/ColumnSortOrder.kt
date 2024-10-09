@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")
package symphony

import kotlinx.JsExport

enum class ColumnSortOrder {
    asc,dsc;

    val isAscending get() = this == asc;
    val isDescending get() = this == dsc;

    val reversed get() = when(this) {
        asc -> dsc
        dsc -> asc
    }
}