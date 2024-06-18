@file:JsExport

package symphony

import kotlinx.JsExport

sealed interface DynamicRowContent {
    val label: BaseField<String>

    val asNode get() = this as? DynamicRowNode
    val asLeaf get() = this as? DynamicRowLeaf
}