@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package symphony

import kotlinx.JsExport

interface GroupedPaginationManager<out G, out T> : PaginationManager<T, GroupedPage<G, T>, GroupedPageFindResult<G, T>> {
    val continuous: List<Chunk<G, Row<T>>>

    suspend fun initialize(ld: PageLoaderFunction<Chunk<@UnsafeVariance G, @UnsafeVariance T>>): GroupedPage<G, T>

    fun onLoad(loader: suspend GroupedPaginationManager<G, T>.(params: PageLoaderParams) -> Unit)

    fun update(chunk: Chunk<@UnsafeVariance G, @UnsafeVariance T>, loading: Boolean = false)

    // --------------------- loopers ----------------------
    fun forEachPage(block: (GroupedPage<G, T>) -> Unit)
}