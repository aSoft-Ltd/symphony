package symphony

import geo.GeoLocation

interface LocationProvider {
    val name: String
    fun transform(input: String?): GeoLocation?
}