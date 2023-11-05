package symphony.internal.memory

import symphony.AbstractPage
import symphony.IPageResult
import symphony.LinearPage
import symphony.LinearPageResult
import symphony.Row

internal abstract class PageMemoryManager<out T, out P : AbstractPage, R : IPageResult<T>>(
    internal val entries: MutableMap<Int, PageMemoryEntry<@UnsafeVariance P>>
) {
    fun save(page: @UnsafeVariance P): P {
        val record = entries.getOrPut(page.capacity) { PageMemoryEntry(page.capacity, mutableMapOf()) }
        record.pages[page.number] = page
        return page
    }

    fun load(page: Int, capacity: Int): P? {
        val record = entries[capacity] ?: return null
        return record.pages[page]
    }

    fun clear() = entries.clear()

    abstract fun load(row: Int, page: Int, capacity: Int): R?

    abstract fun load(item: @UnsafeVariance T, capacity: Int): R?
}