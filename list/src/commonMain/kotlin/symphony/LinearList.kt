@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.Flock
import kollections.List
import kotlin.js.JsExport

interface LinearList<out T> : LazyList<T> {
    val rows: List<Row<T>>
}