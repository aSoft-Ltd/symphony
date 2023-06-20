@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport

data class PrimitiveFieldState<I, O>(
    val name: String,
    val label: Label,
    val hidden: Boolean,
    val hint: String,
    val required: Boolean,
    val input: I,
    val output: O,
    val feedbacks: Feedbacks
)