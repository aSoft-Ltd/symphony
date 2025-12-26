@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package symphony

import kotlinx.JsExport

interface LinearPaginationManager<out T> : PaginationManager<T, LinearPage<T>, LinearPageFindResult<T>> {
    val continuous: List<Row<T>>

    suspend fun initialize(pl: PageLoaderFunction<@UnsafeVariance T>): LinearPage<T>

    // --------------------- loopers ----------------------
    fun forEachPage(block: (LinearPage<T>) -> Unit)
}