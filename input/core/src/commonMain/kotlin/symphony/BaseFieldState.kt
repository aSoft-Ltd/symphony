@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.JsExport

interface BaseFieldState<out O> : FieldState<O> {
    val name: String
//    val label: Label
    val hint: String
}