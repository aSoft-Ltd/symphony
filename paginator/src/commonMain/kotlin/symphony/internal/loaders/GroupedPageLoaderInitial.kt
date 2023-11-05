package symphony.internal.loaders

import symphony.GroupedPageLoader
import symphony.internal.unInitializedError

object GroupedPageLoaderInitial : GroupedPageLoader<Nothing, Nothing> {
    override fun load(page: Int, capacity: Int) = unInitializedError()
}