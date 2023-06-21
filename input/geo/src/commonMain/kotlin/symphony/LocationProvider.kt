package symphony

import geo.GeoLocation

interface LocationProvider<in I, out O : GeoLocation?> {
    val name: String
    fun transform(input: I?): O?
}