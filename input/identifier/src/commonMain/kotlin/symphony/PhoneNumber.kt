@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import geo.Country
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * Abstract away properly phone numbering formats
 * https://en.wikipedia.org/wiki/National_conventions_for_writing_telephone_numbers
 */
@Serializable(with = PhoneSerializer::class)
data class PhoneNumber(
    val country: Country,
    val body: Long
) {
    val bodyAsDouble get() = body.toDouble()
    override fun toString() = "${country.dialingCode.toInt()}${body}"
}