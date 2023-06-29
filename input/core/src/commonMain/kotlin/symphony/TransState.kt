@file:JsExport

package symphony

import kotlin.js.JsExport

interface TransState<out I, out O> : FieldState<O> {
    val input: I?
}