@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import symphony.properties.Settable
import kotlin.js.JsExport

interface PrimitiveField<O> : Field<PrimitiveFieldState<O>>, Settable<O> {
    val label: Label
    val required: Boolean
    val output: O?
    val hint: String
}