package symphony.internal.loaders

import symphony.LinearPageLoader
import symphony.PageLoaderParams
import symphony.internal.unInitializedError

internal object LinearPageLoaderInitial : LinearPageLoader<Nothing> {
    override fun load(params: PageLoaderParams) = unInitializedError()
}