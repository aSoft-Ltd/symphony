@file:Suppress("NOTHING_TO_INLINE")

package presenters.collections

import presenters.collections.internal.RowImpl

@Deprecated("use symphony instead")
inline fun <T> Row(
    index: Int,
    item: T
): Row<T> = RowImpl(index, item)