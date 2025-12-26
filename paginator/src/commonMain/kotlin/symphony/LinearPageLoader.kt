package symphony


interface LinearPageLoader<out T> : PageLoader<LinearPage<T>> {
    override suspend fun load(params: PageLoaderParams): LinearPage<T>
}