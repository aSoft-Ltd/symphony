package symphony.internal.loaders

import symphony.LinearPageLoader
import symphony.PageLoaderParams
import symphony.internal.deInitializedError

internal object LinearPageLoaderFinal : LinearPageLoader<Nothing> {
    override fun load(params: PageLoaderParams) = deInitializedError()
}