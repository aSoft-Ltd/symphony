@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.Map
import kollections.Set
import kotlin.js.JsExport

sealed interface LinearSelected<out T> {
    val none get() = this as? LinearSelectedNone
    val item get() = this as? LinearSelectedItem
    val items get() = this as? LinearSelectedItems
    val global get() = this as? LinearSelectedGlobal
}

data object LinearSelectedNone : LinearSelected<Nothing>

data class LinearSelectedItem<out T>(
    val page: LinearPage<T>,
    val row: Row<T>
) : LinearSelected<T>

data class LinearSelectedItems<out T>(
    val page: Map<LinearPage<@UnsafeVariance T>, Set<Row<T>>>
) : LinearSelected<T>

data class LinearSelectedGlobal<out T>(
    val exceptions: Set<SelectedItem<T>>
) : LinearSelected<T>