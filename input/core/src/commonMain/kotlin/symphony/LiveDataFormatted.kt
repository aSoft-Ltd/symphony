@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.Live
import kotlin.js.JsExport

interface LiveDataFormatted<out I, out D> : LiveData<D> {
    override val data: Live<DataFormatted<I, D>>
}