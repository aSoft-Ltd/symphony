@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport

data class BaseFieldState<out O>(
    val name: String,
    override val label: Label,
    override val hidden: Boolean,
    override val hint: String,
    override val required: Boolean,
    override val output: O?,
    override val feedbacks: Feedbacks
) : FieldState<O>