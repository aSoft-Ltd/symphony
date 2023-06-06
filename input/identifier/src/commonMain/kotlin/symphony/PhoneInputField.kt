@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import geo.Country
import symphony.properties.Typeable
import kotlin.js.JsExport

interface PhoneInputField : InputField, CommonInputProperties, TransformingInputField<String, PhoneNumber>, Typeable {
    val code: SingleChoiceInputField<Country>
    val number: NumberInputField<Long>
}