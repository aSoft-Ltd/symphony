package symphony.internal.loaders

import symphony.GroupedPageLoader
import symphony.PageLoaderParams
import symphony.internal.deInitializedError

internal object GroupedPageLoaderFinal : GroupedPageLoader<Nothing, Nothing> {
    override suspend fun load(params: PageLoaderParams) = throw deInitializedError()
}