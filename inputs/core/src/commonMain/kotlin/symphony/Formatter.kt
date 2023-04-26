@file:JsExport

package symphony

import kotlin.js.JsExport

fun interface Formatter<in O> {
    operator fun invoke(value: O?): String?
}