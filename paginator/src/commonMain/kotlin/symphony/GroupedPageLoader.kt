package symphony

import koncurrent.Later
import koncurrent.later.then
import koncurrent.later.andThen
import koncurrent.later.andZip
import koncurrent.later.zip
import koncurrent.later.catch

interface GroupedPageLoader<out G, out T> : PageLoader<GroupedPage<G, T>> {
    override fun load(page: Int, capacity: Int): Later<GroupedPage<G, T>>
}