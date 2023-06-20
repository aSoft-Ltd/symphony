@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport

data class TextFieldState<T : String?>(
    val name: String,
    val label: Label,
    val hidden: Boolean,
    val hint: String,
    val required: Boolean,
    val output: T,
    val input: String?,
    val feedbacks: Feedbacks
)