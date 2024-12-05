package symphony.internal.loaders

import koncurrent.Later
import symphony.LinearPage
import symphony.LinearPageLoader
import symphony.OnFresh
import symphony.PageLoaderParams
import symphony.PageLoaderSource
import symphony.internal.deInitializedError

internal object LinearPageLoaderFinal : LinearPageLoader<Nothing> {
    override fun load(params: PageLoaderParams, source: PageLoaderSource) = deInitializedError()
}