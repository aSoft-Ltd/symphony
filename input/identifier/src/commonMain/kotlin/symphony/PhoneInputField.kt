package symphony

import geo.Country
import symphony.properties.Typeable

interface PhoneInputField : InputField, CommonInputProperties, TransformingInputField<String, PhoneNumber>, Typeable {
    val code: SingleChoiceInputField<Country>
    val number: NumberInputField<Long>
}