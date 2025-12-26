package symphony.internal.loaders

import symphony.GroupedPageLoader
import symphony.PageLoaderParams
import symphony.internal.unInitializedError

internal object GroupedPageLoaderInitial : GroupedPageLoader<Nothing, Nothing> {
    override suspend fun load(params: PageLoaderParams) = throw unInitializedError()
}