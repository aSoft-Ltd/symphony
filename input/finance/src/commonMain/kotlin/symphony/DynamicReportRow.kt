@file:JsExport

package symphony

import cinematic.Live
import kotlinx.JsExport

interface DynamicReportRow {
    val label: BaseField<String>
    val container: BaseField<Boolean>
    val total: NumberField<Double>
    val rows: Live<List<DynamicReportRow>>
    val removable: Boolean
    val appendable: Boolean
    fun collapse()
    fun expand()
    fun add(name: String = "Other"): DynamicReportRow?
    fun remove(child: DynamicReportRow?): DynamicReportRow?
}