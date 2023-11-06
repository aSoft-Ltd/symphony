@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.Map
import kollections.Set
import kotlin.js.JsExport

sealed interface GroupedSelected<out G, out T> {
    val none get() = this as? GroupedSelectedNone
    val item get() = this as? GroupedSelectedItem
    val items get() = this as? GroupedSelectedItems
    val global get() = this as? GroupedSelectedGlobal
}

data object GroupedSelectedNone : GroupedSelected<Nothing, Nothing>

data class GroupedSelectedItem<out G, out T>(
    val page: GroupedPage<G, T>,
    val row: Row<T>
) : GroupedSelected<G, T>

data class GroupedSelectedItems<out G, out T>(
    val page: Map<GroupedPage<@UnsafeVariance G, @UnsafeVariance T>, Set<Row<T>>>
) : GroupedSelected<G, T>

data class GroupedSelectedGlobal<out G, out T>(
    val exceptions: Set<GroupedSelectedItem<G, T>>
) : GroupedSelected<G, T>