@file:JsExport

package symphony

import kotlin.js.JsExport

interface Data<out D> {
    val output: D?
}