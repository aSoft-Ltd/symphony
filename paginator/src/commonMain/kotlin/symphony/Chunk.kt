@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kotlinx.JsExport

data class Chunk<out G, out D>(
    val group: G,
    val items: List<D>
) {
    fun <R> map(transformer: (D) -> R) = Chunk(group, items.map(transformer))
    fun <R> mapWithIndex(transformer: (item: D, index: Int) -> R) = Chunk(group, items.mapWithIndex(transformer))
}