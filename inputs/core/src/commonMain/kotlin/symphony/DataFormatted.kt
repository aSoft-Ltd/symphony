@file:JsExport

package symphony

import kotlin.js.JsExport

interface DataFormatted<out I, out O> : Data<O> {
    val input: I?
    val formatted: String
}