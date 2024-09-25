@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import nation.Country
import sim.Phone
import kotlinx.JsExport

data class PhoneOutput(
    val country: Country,
    val body: Long
) {
    val bodyAsDouble get() = body.toDouble()
    override fun toString() = "${country.dialingCode.toInt()}${body}"

    fun toPhone() = Phone(code = country.dialingCode, body = body.toString())
}