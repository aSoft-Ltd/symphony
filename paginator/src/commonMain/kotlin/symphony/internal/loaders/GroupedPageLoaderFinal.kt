package symphony.internal.loaders

import symphony.GroupedPage
import symphony.GroupedPageLoader
import symphony.OnFresh
import symphony.PageLoaderParams
import symphony.PageLoaderSource
import symphony.internal.deInitializedError

internal object GroupedPageLoaderFinal : GroupedPageLoader<Nothing, Nothing> {
    override fun load(params: PageLoaderParams, source: PageLoaderSource) = deInitializedError()
}