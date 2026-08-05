@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.JsExport

sealed interface Selected<out T>

data object SelectedNone : Selected<Nothing>

data class SelectedItem<out T>(
    val page: Paged<T>,
    val row: Row<T>
) : Selected<T>

data class SelectedItems<out T>(val page: Map<Paged<@UnsafeVariance T>, Set<Row<T>>>) : Selected<T>

data class SelectedGlobal<out T>(val exceptions: Set<SelectedItem<T>>) : Selected<T>