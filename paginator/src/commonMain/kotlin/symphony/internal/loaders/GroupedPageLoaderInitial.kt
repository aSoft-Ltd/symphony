package symphony.internal.loaders

import symphony.GroupedPageLoader
import symphony.PageLoaderParams
import symphony.internal.unInitializedError

internal object GroupedPageLoaderInitial : GroupedPageLoader<Nothing, Nothing> {
    override fun load(params: PageLoaderParams) = unInitializedError()
}