@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import symphony.properties.Settable
import symphony.properties.Typeable
import symphony.validation.Validateable
import kotlin.js.JsExport
import kotlin.js.JsName

interface NumberInputField<N : Number> : InputField, CommonInputProperties, Settable<String>, LiveDataFormatted<String, N>, Validateable<N>, Typeable {
    val max: N?
    val min: N?
    val step: N?

    fun increment(step: N?)

    fun decrement(step: N?)

    @JsName("setDouble")
    fun set(double: Double)

    @JsName("setInt")
    fun set(integer: Int)
}