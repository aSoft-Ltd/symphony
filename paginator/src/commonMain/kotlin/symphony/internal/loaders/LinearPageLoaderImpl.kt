package symphony.internal.loaders

import kollections.Collection
import kollections.mapIndexed
import kollections.toList
import koncurrent.Later
import koncurrent.later.then
import koncurrent.later.andThen
import koncurrent.later.andZip
import koncurrent.later.zip
import koncurrent.later.catch
import symphony.LinearPage
import symphony.LinearPageLoader
import symphony.PageLoader
import symphony.PageLoaderParams
import symphony.Row

internal class LinearPageLoaderImpl<out T>(
    private val loader: (PageLoaderParams) -> Later<Collection<T>>,
) : LinearPageLoader<T> {
    override fun load(params: PageLoaderParams): Later<LinearPage<T>> = loader(params).then {
        LinearPage(items = it.toList().mapIndexed { index, t -> Row(index, t) }, params.limit, params.page)
    }
}