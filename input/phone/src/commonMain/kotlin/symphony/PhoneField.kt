@file:JsExport

package symphony

import kotlinx.JsName
import kotlinx.JsExport
import nation.Country

interface PhoneField : Field<PhoneOutput,PhoneFieldState>, PhoneFieldState {

    fun options(withSelect: Boolean = false): List<Option>

    fun searchByFiltering(key: String?)

    fun searchByOrdering(key: String?)

    fun clearSearch()

    fun selectCountryOption(option: Option)

    fun selectCountryLabel(optionLabel: String)

    fun selectCountryValue(optionValue: String)

    fun unsetCountry()

    fun setCountry(country: Country?)

    fun setBody(value: String?)

    @JsName("setBodyAsLong")
    fun setBody(long: Long?)
}