@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kotlin.js.JsExport

interface DataList<out D> : Data<List<D>> {
    override val output: List<D>
}