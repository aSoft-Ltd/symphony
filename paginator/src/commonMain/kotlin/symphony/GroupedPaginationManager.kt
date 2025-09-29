@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package symphony

import kollections.List
import koncurrent.Later
import koncurrent.awaited.then
import koncurrent.awaited.andThen
import koncurrent.awaited.andZip
import koncurrent.awaited.zip
import koncurrent.awaited.catch
import kotlinx.JsExport

interface GroupedPaginationManager<out G, out T> : PaginationManager<T, GroupedPage<G, T>, GroupedPageFindResult<G, T>> {
    val continuous: List<Chunk<G, Row<T>>>

    fun initialize(ld: PageLoaderFunction<Chunk<@UnsafeVariance G, @UnsafeVariance T>>): Later<GroupedPage<G, T>>

    // --------------------- loopers ----------------------
    fun forEachPage(block: (GroupedPage<G, T>) -> Unit)
}