@file:Suppress("NOTHING_TO_INLINE")

package symphony.internal

import symphony.GroupedPageFindResult
import symphony.GroupedSelectedItem
import symphony.LinearPageFindResult
import symphony.LinearSelectedItem
import symphony.PageResult
import symphony.SelectedItem

inline fun <T> PageResult<T>.toSelectedItem() = SelectedItem(page, row)

inline fun <T> LinearPageFindResult<T>.toSelectedItem() = LinearSelectedItem(page, row)
inline fun <G, T> GroupedPageFindResult<G, T>.toSelectedItem() = GroupedSelectedItem(page, row)