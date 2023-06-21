@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import symphony.properties.Settable
import kotlin.js.JsExport
import symphony.RangeFieldState as RState

interface RangeField<O : Any, R : Range<O>?> : Field<RState<O>>, Settable<R>, CommonField<Range<O>, RState<O>> {
    fun setStart(value: O?)

    fun setEnd(value: O?)
}