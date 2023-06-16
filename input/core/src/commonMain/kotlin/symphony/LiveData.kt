@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.Live
import kotlin.js.JsExport

interface LiveData<out D>: InputField {
    val data: Live<Data<D>>
    val output: D? get() = data.value.output
}