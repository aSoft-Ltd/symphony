@file:JsExport

package symphony

import geo.GeoLocation
import kollections.JsExport
import symphony.internal.GooglePlacesApiParser

object GoogleLocationProvider : LocationProvider<String, GeoLocation> {
    override val name = "Google"
    private val parser = GooglePlacesApiParser()
    override fun transform(input: String?) = parser.parseOrNull(input)
}