package symphony

import koncurrent.Later
import koncurrent.awaited.then
import koncurrent.awaited.andThen
import koncurrent.awaited.andZip
import koncurrent.awaited.zip
import koncurrent.awaited.catch

interface GroupedPageLoader<out G, out T> : PageLoader<GroupedPage<G, T>> {
    override fun load(params: PageLoaderParams): Later<GroupedPage<G, T>>
}