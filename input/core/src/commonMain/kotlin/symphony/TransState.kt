@file:JsExport

package symphony

import kotlin.js.JsExport

interface TransState<out I, out O> : FState<O> {
    val input: I?
}