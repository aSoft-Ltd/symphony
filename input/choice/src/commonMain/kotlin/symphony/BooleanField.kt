@file:JsExport

package symphony

import symphony.properties.Settable
import kotlin.js.JsExport

interface BooleanField<T : Boolean?> : Field<PrimitiveFieldState<T, T>>, Settable<T> {
    fun toggle()
    val output: T
}