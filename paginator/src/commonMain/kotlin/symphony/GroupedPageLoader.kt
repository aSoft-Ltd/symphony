package symphony


interface GroupedPageLoader<out G, out T> : PageLoader<GroupedPage<G, T>> {
    override suspend fun load(params: PageLoaderParams): GroupedPage<G, T>
}