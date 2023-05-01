package symphony.internal

import symphony.Page

internal data class PageCacheRecord<T>(
    val capacity: Int,
    val pages: MutableMap<Int, Page<T>>
)