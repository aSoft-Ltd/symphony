package symphony.internal.loaders

import symphony.LinearPageLoader
import symphony.internal.unInitializedError

object LinearPageLoaderInitial : LinearPageLoader<Nothing> {
    override fun load(page: Int, capacity: Int) = unInitializedError()
}