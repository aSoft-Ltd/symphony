@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport

interface BaseFieldState<out O> : FState<O> {
    val name: String
    val label: Label
    val hint: String
}