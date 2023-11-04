@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kotlin.js.JsExport

data class Chunk<out G, out D>(
    val group: G,
    val rows: List<Row<D>>
)