@file:JsExport

package symphony

import kotlinx.JsExport

interface DynamicRowLeaf : DynamicRowContent {
    val amount: NumberField<Double>
}