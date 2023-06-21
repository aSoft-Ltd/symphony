@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport

interface FieldState<out O> {
    val output: O?
    val label: Label
    val required: Boolean
    val hint: String
    val hidden: Boolean
    val feedbacks: Feedbacks
}