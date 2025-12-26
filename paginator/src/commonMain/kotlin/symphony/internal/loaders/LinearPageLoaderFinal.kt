package symphony.internal.loaders

import symphony.LinearPageLoader
import symphony.PageLoaderParams
import symphony.internal.deInitializedError

internal object LinearPageLoaderFinal : LinearPageLoader<Nothing> {
    override suspend fun load(params: PageLoaderParams) = throw deInitializedError()
}