@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.Live
import symphony.properties.Clearable
import symphony.properties.Finishable
import symphony.properties.Hideable
import symphony.properties.Resetable
import symphony.properties.Validable
import kotlin.js.JsExport

interface Field<out S> : Hideable, Clearable, Resetable, Validable, Finishable {
    val state: Live<S>
}