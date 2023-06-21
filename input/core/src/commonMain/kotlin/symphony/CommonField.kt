@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport

interface CommonField<out O, S : FieldState<O>> : Field<S>, FieldState<O>