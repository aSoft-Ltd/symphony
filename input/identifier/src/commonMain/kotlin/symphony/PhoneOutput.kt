@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import geo.Country
import kotlin.js.JsExport

data class PhoneOutput(
    val country: Country,
    val body: Long
) {
    val bodyAsDouble get() = body.toDouble()
    override fun toString() = "${country.dialingCode.toInt()}${body}"
}