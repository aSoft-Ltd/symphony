@file:JsExport

package symphony

import kotlin.js.JsExport

interface FieldState<out O> {
    val output: O
}