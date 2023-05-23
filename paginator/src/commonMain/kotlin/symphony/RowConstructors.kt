@file:Suppress("NOTHING_TO_INLINE")

package symphony

import symphony.internal.RowImpl

inline fun <T> Row(
    index: Int,
    item: T
): Row<T> = Row(pageCapacity = 0, pageNumber = 0, index, item)

inline fun <T> Row(
    pageCapacity: Int,
    pageNumber: Int,
    index: Int,
    item: T
): Row<T> = RowImpl(((pageNumber - 1) * pageCapacity) + index, item)