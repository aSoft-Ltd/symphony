package symphony.internal.loaders

import symphony.LinearPageLoader
import symphony.internal.deInitializedError

object LinearPageLoaderFinal : LinearPageLoader<Nothing> {
    override fun load(page: Int, capacity: Int) = deInitializedError()
}