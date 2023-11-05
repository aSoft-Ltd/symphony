package symphony.internal.memory

internal data class PageMemoryEntry<out P>(
    val capacity: Int,
    val pages: MutableMap<Int, @UnsafeVariance P>
)