@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import symphony.properties.Settable
import kotlin.js.JsExport

interface TextField<T : String?> : Field<PrimitiveFieldState<String?, T>>, Settable<T> {

    override val hidden get() = state.value.hidden
    val output get() = state.value.output

    val feedbacks get() = state.value.feedbacks
}