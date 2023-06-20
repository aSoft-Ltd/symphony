@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import symphony.properties.Settable
import kotlin.js.JsExport

interface TransformingField<I, O> : Field<TransformingFieldState<I, O>>, Settable<I> {
    val transformer: (I) -> O
    val label: Label
    val required: Boolean
    val input: I?
    val output: O?
    val hint: String
}