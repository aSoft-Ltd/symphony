package symphony.internal.memory

import symphony.GroupedPage
import symphony.GroupedPageResult
import symphony.LinearPage
import symphony.LinearPageResult
import symphony.Row

internal class GroupedPageMemoryManager<G, T> : PageMemoryManager<T, GroupedPage<G, T>, GroupedPageResult<G, T>>(mutableMapOf()) {

    override fun load(row: Int, page: Int, capacity: Int): GroupedPageResult<G, T>? {
        val p = load(page, capacity) ?: return null
        val r = p.groups.flatMap { it.items }.firstOrNull { it.number == row } ?: return null
        return GroupedPageResult(p, r)
    }

    override fun load(item: T, capacity: Int): GroupedPageResult<G, T>? {
        val record = entries[capacity] ?: return null
        var page: GroupedPage<G, T>? = null
        var row: Row<T>? = null
        val pages = record.pages.values
        for (p in pages) {
            if (row != null) break
            page = p
            row = p.groups.flatMap { it.items }.find { it.item == item }
        }
        if (page == null) return null
        if (row == null) return null
        return GroupedPageResult(page, row)
    }
}