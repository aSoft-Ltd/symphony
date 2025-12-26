@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.JsExport

interface LinearList<out T> : LazyList<T> {
    val rows: List<Row<T>>
}