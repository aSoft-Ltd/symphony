@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.selected

import kotlinx.JsExport
import symphony.GroupedPage
import symphony.Row

sealed interface GroupedSelected<out G, out T> : Selected<T> {
    override val none get() = this as? GroupedSelectedNone
    override val item get() = this as? GroupedSelectedItem
    override val items get() = this as? GroupedSelectedItems
    override val global get() = this as? GroupedSelectedGlobal
}

data object GroupedSelectedNone : GroupedSelected<Nothing, Nothing>, SelectedNone

data class GroupedSelectedItem<out G, out T>(
    override val page: GroupedPage<G, T>,
    override val row: Row<T>
) : GroupedSelected<G, T>, SelectedItem<T>

data class GroupedSelectedItems<out G, out T>(
    override val page: Map<GroupedPage<@UnsafeVariance G, @UnsafeVariance T>, Set<Row<T>>>
) : GroupedSelected<G, T>, SelectedItems<T>

data class GroupedSelectedGlobal<out G, out T>(
    override val exceptions: Set<GroupedSelectedItem<G, T>>
) : GroupedSelected<G, T>, SelectedGlobal<T>