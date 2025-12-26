@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.JsExport

interface GroupedList<out G, out T> : LazyList<T> {
    val groups: List<Chunk<G, Row<T>>>
}