@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import symphony.properties.Hideable
import symphony.properties.Settable
import kotlin.js.JsExport

interface TextField : Field<TextFieldState>, Settable<String>, Hideable {
    fun clear()

    fun reset()

    fun stop()

    override val hidden get() = state.value.hidden
    val output get() = state.value.output
}