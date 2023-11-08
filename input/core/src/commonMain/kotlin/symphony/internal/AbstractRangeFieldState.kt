@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")
package symphony.internal

import kotlin.js.JsExport
import symphony.Feedbacks
import symphony.Range
import symphony.RangeFieldState
import symphony.Visibility

data class AbstractRangeFieldState<out O : Any>(
    val name: String,
    override val start: O?,
    override val end: O?,
    override val visibility: Visibility,
    override val required: Boolean,
    override val feedbacks: Feedbacks
) : RangeFieldState<O> {
    override val input get() = Range(start, end)
    override val output get() = if (start != null && end != null) Range(start, end) else null
}