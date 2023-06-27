@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import geo.Country
import kotlin.js.JsExport

interface PhoneFieldState : FState<PhoneOutput> {
    val name: String
    val label: Label
    val hint: String
    val country: Country?
    val body: Long?
}