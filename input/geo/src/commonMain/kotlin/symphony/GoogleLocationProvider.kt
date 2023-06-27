@file:JsExport

package symphony

import kollections.JsExport
import symphony.internal.GooglePlacesApiParser

object GoogleLocationProvider : LocationProvider {
    override val name = "Google"
    private val parser = GooglePlacesApiParser()
    override fun transform(input: String?) = parser.parseOrNull(input)
}