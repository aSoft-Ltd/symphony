@file:Suppress("NOTHING_TO_INLINE")

package symphony

import kollections.toIList
import symphony.internal.PageImpl

inline fun <T> Page(
    items: Collection<T> = listOf(),
    capacity: Int = items.size,
    number: Int = 1
): Page<T> = PageImpl(
    items = items.mapIndexed { index, it ->
        Row(capacity, number, index, it)
    }.toIList(),
    capacity = capacity,
    number = number
)