@file:JsExport

package symphony

import kollections.List
import kotlinx.JsExport

sealed interface RowTemplate {
    val label: String
    val bold: Boolean

    val asGroupedRowField get() = this as? GroupedRowTemplate
    val asCapturedRowField get() = this as? CapturedRowTemplate
    val asComputedRowField get() = this as? ComputedRowTemplate
}

data class GroupedRowTemplate(
    override val label: String,
    override val bold: Boolean,
    val children: List<RowTemplate>
) : RowTemplate

data class CapturedRowTemplate(
    override val label: String,
    override val bold: Boolean,
) : RowTemplate

data class ComputedRowTemplate(
    override val label: String,
    override val bold: Boolean,
    val updater: (CellGetter) -> Double
) : RowTemplate