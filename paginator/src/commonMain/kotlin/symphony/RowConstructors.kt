@file:Suppress("NOTHING_TO_INLINE")

package symphony

import symphony.internal.RowImpl

inline fun <T> Row(
    index: Int,
    item: T
): Row<T> = RowImpl(index, item)