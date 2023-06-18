@file:JsExport

package symphony

import cinematic.Live
import kotlin.js.JsExport

@Suppress("NON_EXPORTABLE_TYPE")
interface Field<out S> {
    val state: Live<S>
}