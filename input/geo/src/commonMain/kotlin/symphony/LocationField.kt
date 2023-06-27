@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import geo.GeoLocation
import kotlin.js.JsExport

interface LocationField : TransformingField<String, GeoLocation> {
    val provider: LocationProvider
}