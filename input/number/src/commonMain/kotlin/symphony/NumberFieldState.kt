@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport

data class NumberFieldState<N : Number?>(
    val name: String,
    val label: Label,
    val hidden: Boolean,
    val hint: String,
    val required: Boolean,
    val input: String?,
    val output: N?,
    val feedbacks: Feedbacks
)