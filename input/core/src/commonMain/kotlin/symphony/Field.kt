@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.Live
import symphony.properties.Finishable
import kotlin.js.JsExport

interface Field<out S> : Finishable {
    val state: Live<S>
}