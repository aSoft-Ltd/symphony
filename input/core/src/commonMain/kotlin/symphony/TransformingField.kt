@file:JsExport

package symphony

import symphony.properties.Settable
import kotlin.js.JsExport
import symphony.TransformingFieldState as TState

interface TransformingField<I, O> : Field<TState<I, O>>, Settable<I>, CommonField<O, TState<I, O>> {
    val transformer: (I) -> O?
    val input: I?
}