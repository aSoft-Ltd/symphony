@file:JsExport

package symphony

import cinematic.Live
import kollections.List
import kotlinx.JsExport

interface DynamicReportRow {
    val label: BaseField<String>
    val container: BaseField<Boolean>
    val total: NumberField<Double>
    val rows: Live<List<DynamicReportRow>>
    fun collapse()
    fun expand()
    fun add(name: String = "Other"): DynamicReportRow?
    fun remove(child: DynamicReportRow?): DynamicReportRow?
}