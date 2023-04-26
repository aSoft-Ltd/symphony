@file:JsExport

package presenters

import kotlin.js.JsExport

@Deprecated("use symphony")
interface Data<out D> {
    val output: D?
}