@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import geo.Country
import kollections.List
import symphony.properties.Settable
import kotlin.js.JsExport
import kotlin.js.JsName

interface PhoneField<P : PhoneOutput?> : Field<PhoneFieldState>, Settable<P> {

    val output: P?

    val label get() = state.value.label

    val required get() = state.value.required

    val hint get() = state.value.hint

    val countries: List<Country>

    val selectedCountry: Country?

    val selectedOption: Option?

    fun options(withSelect: Boolean = false): List<Option>

    fun selectCountryOption(option: Option)

    fun selectCountryLabel(optionLabel: String)

    fun selectCountryValue(optionValue: String)

    fun unsetCountry()

    fun setCountry(country: Country?)

    fun setBody(value: String?)

    @JsName("setBodyAsLong")
    fun setBody(long: Long?)
}