@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import symphony.properties.Clearable
import symphony.properties.Hideable
import symphony.properties.Resetable
import symphony.properties.Settable
import symphony.properties.Validable
import kotlin.js.JsExport

interface TextField<T : String?> : Field<TextInputState<T>>, Hideable, Clearable, Resetable, Validable, Settable<T> {

    override val hidden get() = state.value.hidden
    val output get() = state.value.output

    val feedbacks get() = state.value.feedbacks
}