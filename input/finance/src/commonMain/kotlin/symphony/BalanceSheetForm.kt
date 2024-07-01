@file:JsExport

package symphony

import kotlinx.JsExport

interface BalanceSheetForm {
    val assets: DynamicReportRow
    val equity: DynamicReportRow
    val liabilities: DynamicReportRow
}