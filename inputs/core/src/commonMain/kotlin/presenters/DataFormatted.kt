@file:JsExport

package presenters

import kotlin.js.JsExport

@Deprecated("use symphony")
interface DataFormatted<out I, out O> : Data<O> {
    val input: I?
    val formatted: String
}