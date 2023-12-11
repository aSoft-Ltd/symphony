@file:JsExport

package symphony

import kotlinx.JsExport

interface TransState<out I, out O> : FieldState<O> {
    val input: I?
}