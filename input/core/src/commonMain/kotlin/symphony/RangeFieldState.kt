@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport

data class RangeFieldState<O>(
    val name: String,
    val start: O?,
    val end: O?,
    override val label: Label,
    override val hidden: Boolean,
    override val hint: String,
    override val required: Boolean,
    override val feedbacks: Feedbacks
) : FieldState<Range<O>> {
    val input get() = Range(start, end)
    override val output get() = if (start != null && end != null) Range(start, end) else null
}