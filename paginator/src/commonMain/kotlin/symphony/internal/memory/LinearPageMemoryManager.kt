package symphony.internal.memory

import kollections.firstOrNull
import kollections.iterator
import symphony.LinearPage
import symphony.LinearPageFindResult
import symphony.PageLoaderParams

internal class LinearPageMemoryManager<T> : PageMemoryManager<T, LinearPage<T>, LinearPageFindResult<T>>(mutableMapOf()) {

    override fun load(row: Int, params: PageLoaderParams): LinearPageFindResult<T>? {
        val p = load(params) ?: return null
        val r = p.items.firstOrNull { it.number == row } ?: return null
        return LinearPageFindResult(p, r)
    }

    override fun load(item: T): LinearPageFindResult<T>? {
        for (page in entries.values) {
            for (row in page.items.iterator()) {
                if (item != row.item) continue
                return LinearPageFindResult(page, row)
            }
        }
        return null
    }
}