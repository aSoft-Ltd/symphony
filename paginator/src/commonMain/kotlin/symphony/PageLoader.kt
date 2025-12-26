package symphony

sealed interface PageLoader<out P : Page> {
    suspend fun load(params: PageLoaderParams): P
}