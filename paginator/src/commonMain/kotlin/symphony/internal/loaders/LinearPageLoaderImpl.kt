package symphony.internal.loaders

import kollections.mapIndexed
import kollections.toList
import koncurrent.Later
import koncurrent.later.then
import symphony.LinearPage
import symphony.LinearPageLoader
import symphony.OnFresh
import symphony.PageLoaderFunction
import symphony.PageLoaderParams
import symphony.PageLoaderSource
import symphony.Row

internal class LinearPageLoaderImpl<out T>(
    private val loader: PageLoaderFunction<T>,
) : LinearPageLoader<T> {
    override fun load(params: PageLoaderParams, source: PageLoaderSource): Later<LinearPage<T>> = loader(params, source).then {
        LinearPage(items = it.toList().mapIndexed { index, t -> Row(index, t) }, params.limit, params.page)
    }
}