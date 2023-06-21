@file:JsExport

package symphony

import geo.GeoLocation
import kollections.JsExport
import symphony.internal.GooglePlacesApiParser

class GoogleLocationProvider<G : GeoLocation?> : LocationProvider<String, G> {
    override val name = "Google"
    private val parser = GooglePlacesApiParser()
    override fun transform(input: String?) = parser.parseOrNull(input) as? G
}