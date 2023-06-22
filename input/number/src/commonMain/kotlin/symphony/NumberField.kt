@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport
import kotlin.js.JsName

interface NumberField<N : Number?> : BaseField<N> {

    fun increment(step: N? = null)

    fun decrement(step: N? = null)

    @JsName("setNumber")
    fun set(double: Double?)

    @JsName("setText")
    fun set(text: String?)
}