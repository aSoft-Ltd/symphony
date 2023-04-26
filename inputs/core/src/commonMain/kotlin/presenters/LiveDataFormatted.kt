@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters

import live.Live
import kotlin.js.JsExport

@Deprecated("use symphony")
interface LiveDataFormatted<out I, out D> : LiveData<D> {
    override val data: Live<DataFormatted<I, D>>
}