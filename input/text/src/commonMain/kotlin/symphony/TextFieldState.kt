@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kotlin.js.JsExport

data class TextFieldState(
    val name: String,
    val label: Label,
    val hidden: Boolean,
    val hint: String,
    val required: Boolean,
    val output: String,
    val input: String,
    val suggestions: List<String>,
    val feedback: Feedbacks
)