@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package symphony

import kollections.List
import koncurrent.Later
import kotlinx.JsExport

interface GroupedPaginationManager<out G, out T> : PaginationManager<T, GroupedPage<G, T>, GroupedPageFindResult<G, T>> {
    val continuous: List<Chunk<G, Row<T>>>

    fun initialize(ld: PageLoaderFunction<Chunk<@UnsafeVariance G, @UnsafeVariance T>>): Later<GroupedPage<G, T>>

    // --------------------- loopers ----------------------
    fun forEachPage(block: (GroupedPage<G, T>) -> Unit)
}