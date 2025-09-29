package symphony.internal.loaders

import koncurrent.Later
import koncurrent.awaited.then
import koncurrent.awaited.andThen
import koncurrent.awaited.andZip
import koncurrent.awaited.zip
import koncurrent.awaited.catch
import kollections.Collection
import kollections.map
import symphony.Chunk
import symphony.GroupedPage
import symphony.GroupedPageLoader
import symphony.Page
import symphony.PageLoaderParams
import symphony.Row

internal class GroupedPageLoaderImpl<out G, out T>(
    private val loader: (PageLoaderParams) -> Later<Collection<Chunk<G, T>>>
) : GroupedPageLoader<G,T> {
    override fun load(params: PageLoaderParams): Later<GroupedPage<G, T>> = loader(params).then { chunks ->
        val groups = chunks.map { chunk -> chunk.mapIndexed { item, index -> Row(index, item) } }
        GroupedPage(groups, params.limit, params.page)
    }
}