@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.JsExport
import kotlin.js.JsName

interface TransformingNumberField<I : Number?, O> : TransformingField<I, O> {

    fun increment(step: I? = null)

    fun decrement(step: I? = null)

    @JsName("setNumber")
    fun set(double: Double?)

    @JsName("setText")
    fun set(text: String?)
}