package symphony.internal.loaders

import kollections.toIList
import koncurrent.Later
import symphony.LinearPage
import symphony.LinearPageLoader
import symphony.Row

internal class LinearPageLoaderImpl<out T>(
    private val loader: (no: Int, capacity: Int) -> Later<Collection<T>>
) : LinearPageLoader<T> {
    override fun load(page: Int, capacity: Int): Later<LinearPage<T>> = loader(page, capacity).then {
        LinearPage(items = it.mapIndexed { index, t -> Row(index, t) }.toIList(), capacity, page)
    }
}