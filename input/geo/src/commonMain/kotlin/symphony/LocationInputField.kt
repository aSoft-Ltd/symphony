@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import geo.GeoLocation
import symphony.properties.Typeable
import kotlin.js.JsExport

interface LocationInputField : TransformingInputField<String, GeoLocation>, Typeable