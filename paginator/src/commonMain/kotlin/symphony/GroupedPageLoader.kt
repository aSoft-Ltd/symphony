package symphony

import koncurrent.Later

interface GroupedPageLoader<out G, out T> : IPageLoader<GroupedPage<G, T>> {
    override fun load(page: Int, capacity: Int): Later<GroupedPage<G, T>>
}