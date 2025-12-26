package symphony.internal.loaders

import symphony.LinearPage
import symphony.LinearPageLoader
import symphony.PageLoaderParams
import symphony.Row

internal class LinearPageLoaderImpl<out T>(
    private val loader: suspend (PageLoaderParams) -> Collection<T>,
) : LinearPageLoader<T> {
    override suspend fun load(params: PageLoaderParams): LinearPage<T> {
        val data = loader(params).mapIndexed { index, t -> Row(index, t) }
        return LinearPage(items = data, params.limit, params.page)
    }
}