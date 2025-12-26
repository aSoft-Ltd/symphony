package symphony.internal.loaders

import symphony.LinearPageLoader
import symphony.PageLoaderParams
import symphony.internal.unInitializedError

internal object LinearPageLoaderInitial : LinearPageLoader<Nothing> {
    override suspend fun load(params: PageLoaderParams) = throw unInitializedError()
}