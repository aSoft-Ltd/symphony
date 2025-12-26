package symphony.internal.memory

import symphony.GroupedPage
import symphony.GroupedPageFindResult
import symphony.PageLoaderParams

internal class GroupedPageMemoryManager<G, T> : PageMemoryManager<T, GroupedPage<G, T>, GroupedPageFindResult<G, T>>(mutableMapOf()) {

    override fun load(row: Int, params: PageLoaderParams): GroupedPageFindResult<G, T>? {
        val p = load(params) ?: return null
        val r = p.groups.flatMap { it.items }.firstOrNull { it.number == row } ?: return null
        return GroupedPageFindResult(p, r)
    }

    override fun load(item: T): GroupedPageFindResult<G, T>? {
        for (page in entries.values) {
            for (row in page.groups.flatMap { it.items }.iterator()) {
                if (item != row.item) continue
                return GroupedPageFindResult(page, row)
            }
        }
        return null
    }
}