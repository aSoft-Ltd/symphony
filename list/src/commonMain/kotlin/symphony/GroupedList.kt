@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kotlin.js.JsExport

interface GroupedList<out G, out T> : LazyList<T> {
    val groups: List<Chunk<G, Row<T>>>
}