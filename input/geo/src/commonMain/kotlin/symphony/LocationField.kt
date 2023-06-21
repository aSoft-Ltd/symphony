@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import geo.GeoLocation
import kotlin.js.JsExport

interface LocationField<I, O : GeoLocation?> : TransformingField<I, O> {
    val provider: LocationProvider<I, O>
}