@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.Live
import kotlinx.JsExport
import kotlin.js.JsName

interface Paginator<out T> {
    val state: Live<PagedData<T>>

    fun setPageCapacity(cap: Int)
    fun update(data: PagedData<@UnsafeVariance T>)

    suspend fun initialize(block: suspend Paginator<@UnsafeVariance T>.(params: PaginationParams) -> Unit) : PagedData<T>

    // --------------------- loaders ---------------
    suspend fun refresh(): PagedData<T>
    suspend fun loadNextPage(): PagedData<T>
    suspend fun loadPreviousPage(): PagedData<T>
    suspend fun load(page: Int): PagedData<T>
    suspend fun loadFirstPage(): PagedData<T> = load(page = 1)
    suspend fun loadLastPage(): PagedData<T> = load(page = -1)

    // ---------------------- finders -----------------------
//    @JsName("findRow")
//    fun find(row: Int, page: Int): R?
//
//    @JsName("findItem")
//    fun find(item: @UnsafeVariance T): R?

    @JsName("findPage")
    fun find(page: Int): PagedData<T>?

    fun finalize()
}