@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import geo.Country
import kotlin.js.JsExport

data class PhoneFieldState(
    val name: String,
    val label: Label,
    val hidden: Boolean,
    val hint: String,
    val required: Boolean,
    val country: Country?,
    val body: Long?,
    val feedbacks: Feedbacks
) {
    val output get() = if (country != null && body != null) PhoneOutput(country, body) else null
}