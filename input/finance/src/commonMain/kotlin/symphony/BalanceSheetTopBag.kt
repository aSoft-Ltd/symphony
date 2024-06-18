@file:JsExport

package symphony

import cinematic.Live
import kotlinx.JsExport

interface BalanceSheetTopBag {
    val label: String
    val current: StaticSectionRowForm
    val fixed: StaticSectionRowForm
    val total: Live<Double>
}