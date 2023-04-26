@file:JsExport

package presenters

import kotlin.js.JsExport

@Deprecated("use symphony")
fun interface Formatter<in O> {
    operator fun invoke(value: O?): String?
}