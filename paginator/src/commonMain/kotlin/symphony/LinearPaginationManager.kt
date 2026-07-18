@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package symphony

import kotlinx.JsExport

interface LinearPaginationManager<out T> : PaginationManager<T, LinearPage<T>, LinearPageFindResult<T>> {
    val continuous: List<Row<T>>

    @Deprecated("In favour of setup")
    suspend fun initialize(pl: PageLoaderFunction<@UnsafeVariance T>): LinearPage<T>

    suspend fun setup(loader: suspend LinearPaginationManager<T>.(params: PageLoaderParams) -> Unit): LinearPage<T>
    fun update(data: List<@UnsafeVariance T>, loading: Boolean = false)

    // --------------------- loopers ----------------------
    fun forEachPage(block: (LinearPage<T>) -> Unit)
}