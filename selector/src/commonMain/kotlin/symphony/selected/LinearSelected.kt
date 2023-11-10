@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.selected

import kollections.Map
import kollections.Set
import kotlin.js.JsExport
import symphony.LinearPage
import symphony.Row

sealed interface LinearSelected<out T> : Selected<T>{
    override val none get() = this as? LinearSelectedNone
    override val item get() = this as? LinearSelectedItem
    override val items get() = this as? LinearSelectedItems
    override val global get() = this as? LinearSelectedGlobal
}

data object LinearSelectedNone : LinearSelected<Nothing>, SelectedNone

data class LinearSelectedItem<out T>(
    override val page: LinearPage<T>,
    override val row: Row<T>
) : LinearSelected<T>, SelectedItem<T>

data class LinearSelectedItems<out T>(
    override val page: Map<LinearPage<@UnsafeVariance T>, Set<Row<T>>>
) : LinearSelected<T>, SelectedItems<T>

data class LinearSelectedGlobal<out T>(
    override val exceptions: Set<LinearSelectedItem<T>>
) : LinearSelected<T>, SelectedGlobal<T>