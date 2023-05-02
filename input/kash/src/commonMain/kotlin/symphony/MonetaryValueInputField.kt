@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kash.Money
import symphony.properties.Typeable
import kotlin.js.JsExport
import kotlin.js.JsName

interface MonetaryValueInputField<V : Money> : TransformingInputField<String, V>, Typeable {
    @JsName("setAmountString")
    fun setAmount(value: String?)

    fun setAmount(number: Double?)

    @JsName("setAmountInt")
    fun setAmount(number: Int?)
}