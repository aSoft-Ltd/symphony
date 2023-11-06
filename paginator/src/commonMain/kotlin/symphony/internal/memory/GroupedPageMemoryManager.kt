package symphony.internal.memory

import symphony.GroupedPage
import symphony.GroupedPageFindResult
import symphony.Row

internal class GroupedPageMemoryManager<G, T> : PageMemoryManager<T, GroupedPage<G, T>, GroupedPageFindResult<G, T>>(mutableMapOf()) {

    override fun load(row: Int, page: Int, capacity: Int): GroupedPageFindResult<G, T>? {
        val p = load(page, capacity) ?: return null
        val r = p.groups.flatMap { it.items }.firstOrNull { it.number == row } ?: return null
        return GroupedPageFindResult(p, r)
    }

    override fun load(item: T, capacity: Int): GroupedPageFindResult<G, T>? {
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
        return GroupedPageFindResult(page, row)
    }
}