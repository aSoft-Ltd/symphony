package symphony.internal.memory

import symphony.LinearPage
import symphony.LinearPageResult
import symphony.Row

internal class LinearPageMemoryManager<T> : PageMemoryManager<T, LinearPage<T>, LinearPageResult<T>>(mutableMapOf()) {

    override fun load(row: Int, page: Int, capacity: Int): LinearPageResult<T>? {
        val p = load(page, capacity) ?: return null
        val r = p.items.firstOrNull { it.number == row } ?: return null
        return LinearPageResult(p, r)
    }

    override fun load(item: T, capacity: Int): LinearPageResult<T>? {
        val record = entries[capacity] ?: return null
        var page: LinearPage<T>? = null
        var row: Row<T>? = null
        val pages = record.pages.values
        for (p in pages) {
            if (row != null) break
            page = p
            row = p.items.find { it.item == item }
        }
        if (page == null) return null
        if (row == null) return null
        return LinearPageResult(page, row)
    }
}