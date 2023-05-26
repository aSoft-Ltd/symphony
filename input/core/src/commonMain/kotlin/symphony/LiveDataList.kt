@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.Live
import kollections.List
import kotlin.js.JsExport

interface LiveDataList<out D> : LiveData<List<D>> {
    override val data: Live<DataList<D>>
    override val output: List<D> get() = data.value.output
}