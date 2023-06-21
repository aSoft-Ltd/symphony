@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import symphony.properties.Settable
import kotlin.js.JsExport

interface PrimitiveField<O> : Field<PrimitiveFieldState<O>>, CommonField<O,PrimitiveFieldState<O>>, Settable<O>