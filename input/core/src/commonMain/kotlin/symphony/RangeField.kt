@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.JsExport
import symphony.RangeFieldState as RState

interface RangeField<O : Any> : Field<Range<O>,RState<O>> {
    fun setStart(value: O?)

    fun setEnd(value: O?)
}