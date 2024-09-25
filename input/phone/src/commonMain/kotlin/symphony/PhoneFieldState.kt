@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import nation.Country
import kollections.List
import kotlinx.JsExport

interface PhoneFieldState : FieldState<PhoneOutput> {
    val name: String
    val label: Label
    val countries : List<Country>
    val hint: String
    val country: Country?
    val body: Long?
    val option: Option?
}