@file:JsExport
package symphony

import kotlinx.JsExport

interface BalanceSheetForm {
    val assets: BalanceSheetTopBag
    val equity: StaticSectionRowForm
    val liabilities: BalanceSheetTopBag
}