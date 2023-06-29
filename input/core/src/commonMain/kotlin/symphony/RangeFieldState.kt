@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport

interface RangeFieldState<out O : Any> : FieldState<Range<O>> {
    val start: O?
    val end: O?
    override val visibility: Visibility
    override val required: Boolean
    override val feedbacks: Feedbacks
    val input: Range<O?>
    override val output: Range<O>?
}