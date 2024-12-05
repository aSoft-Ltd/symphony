package symphony.internal.loaders

import koncurrent.Later
import symphony.LinearPage
import symphony.LinearPageLoader
import symphony.OnFresh
import symphony.PageLoaderParams
import symphony.PageLoaderSource
import symphony.internal.unInitializedError

internal object LinearPageLoaderInitial : LinearPageLoader<Nothing> {
    override fun load(params: PageLoaderParams, source: PageLoaderSource) = unInitializedError()
}