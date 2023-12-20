package symphony.internal.loaders

import koncurrent.Later
import kollections.Collection
import kollections.map
import symphony.Chunk
import symphony.GroupedPage
import symphony.GroupedPageLoader
import symphony.Row

class GroupedPageLoaderImpl<out G, out T>(
    private val loader: (no: Int, capacity: Int) -> Later<Collection<Chunk<G, T>>>
) : GroupedPageLoader<G,T> {
    override fun load(page: Int, capacity: Int): Later<GroupedPage<G, T>> = loader(page, capacity).then { chunks ->
        val groups = chunks.map { chunk -> chunk.mapIndexed { item, index -> Row(index, item) } }
        GroupedPage(groups, capacity, page)
    }
}