package symphony.internal.loaders

import symphony.GroupedPageLoader
import symphony.internal.deInitializedError

object GroupedPageLoaderFinal : GroupedPageLoader<Nothing, Nothing> {
    override fun load(page: Int, capacity: Int) = deInitializedError()
}