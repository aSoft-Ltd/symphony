@file:JsExport

package symphony

import kotlinx.JsExport
//import krono.LocalDateField

interface BalanceSheetForm {
//    val date: LocalDateField
    val assets: DynamicReportRow
    val equity: DynamicReportRow
    val liabilities: DynamicReportRow
}