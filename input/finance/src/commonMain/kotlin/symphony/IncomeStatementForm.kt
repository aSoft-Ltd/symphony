@file:JsExport

package symphony

import cinematic.Live
import kotlinx.JsExport

interface IncomeStatementForm {
    val revenue: DynamicReportRow
    val cogs: DynamicReportRow
    val grossProfit: Live<Double>
    val expenses: DynamicReportRow
    val taxes: DynamicReportRow
}