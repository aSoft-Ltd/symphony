@file:Suppress("NOTHING_TO_INLINE")

package symphony.internal

import symphony.PageResult
import symphony.SelectedItem

inline fun <T> PageResult<T>.toSelectedItem() = SelectedItem(page, row)