@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import symphony.properties.Settable
import kotlin.js.JsExport

interface BaseField<O> : Field<BaseFieldState<O>>, CommonField<O,BaseFieldState<O>>, Settable<O>