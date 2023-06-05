package symphony

import geo.Country

interface PhoneInputField : BasicTextInputField {
    val code: SingleChoiceInputField<Country>
}