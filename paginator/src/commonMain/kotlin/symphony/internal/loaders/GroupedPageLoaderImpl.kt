package symphony.internal.loaders

import symphony.Chunk
import symphony.GroupedPage
import symphony.GroupedPageLoader
import symphony.PageLoaderParams
import symphony.Row

internal class GroupedPageLoaderImpl<out G, out T>(
    private val loader: suspend (PageLoaderParams) -> Collection<Chunk<G, T>>
) : GroupedPageLoader<G, T> {
    override suspend fun load(params: PageLoaderParams): GroupedPage<G, T> {
        val chunks = loader(params)
        val groups = chunks.map { chunk -> chunk.mapIndexed { item, index -> Row(index, item) } }
        return GroupedPage(groups, params.limit, params.page)
    }
}