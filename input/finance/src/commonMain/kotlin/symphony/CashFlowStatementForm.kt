@file:JsExport

package symphony

import kotlinx.JsExport

interface CashFlowStatementForm {
    val operating: DynamicReportRow
    val investment: DynamicReportRow
    val financing: DynamicReportRow
}