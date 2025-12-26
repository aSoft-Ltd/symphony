@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.selected

import kotlinx.JsExport
import symphony.Page
import symphony.Row

sealed interface Selected<out T> {
    val none get() = this as? SelectedNone
    val item get() = this as? SelectedItem
    val items get() = this as? SelectedItems
    val global get() = this as? SelectedGlobal
}

sealed interface SelectedNone : Selected<Nothing>

sealed interface SelectedItem<out T> : Selected<T> {
    val page: Page
    val row: Row<T>
}

sealed interface SelectedItems<out T> : Selected<T> {
    val page: Map<*, Set<Row<T>>>
}

sealed interface SelectedGlobal<out T> : Selected<T> {
    val exceptions: Set<SelectedItem<T>>
}