@file:Suppress("NOTHING_TO_INLINE")

package symphony.internal

import symphony.FoundItem
import symphony.GroupedPageFindResult
import symphony.selected.GroupedSelectedItem
import symphony.LinearPageFindResult
import symphony.SelectedItem
import symphony.selected.LinearSelectedItem

inline fun <T> LinearPageFindResult<T>.toSelectedItem() = LinearSelectedItem(page, row)
inline fun <G, T> GroupedPageFindResult<G, T>.toSelectedItem() = GroupedSelectedItem(page, row)

inline fun <T> FoundItem<T>.toSelectedItem() = SelectedItem(paged, row)