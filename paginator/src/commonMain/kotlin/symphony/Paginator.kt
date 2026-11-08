@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.Live
import kotlinx.JsExport
import kotlin.js.JsName

interface Paginator<out T> {
    val state: Live<Paged<T>>
    val params: Live<PaginationParams>

    fun setPageCapacity(cap: Int)
    fun update(data: Paged<@UnsafeVariance T>)

    suspend fun initialize(retries: Int = 3, block: suspend Paginator<@UnsafeVariance T>.(params: PaginationParams) -> Unit): Paged<T>

    // --------------------- loaders ---------------
    suspend fun refresh(retries: Int = 3): Paged<T>
    suspend fun loadNextPage(retries: Int = 3): Paged<T>
    suspend fun loadPreviousPage(retries: Int = 3): Paged<T>
    suspend fun load(page: Int,retries: Int = 3): Paged<T>
    suspend fun loadFirstPage(retries: Int = 3): Paged<T> = load(page = 1,retries)
    suspend fun loadLastPage(retries: Int = 3): Paged<T> = load(page = -1,retries)

    // ---------------------- finders -----------------------
    @JsName("findRow")
    fun find(row: Int, page: Int): FoundItem<T>?

    @JsName("findItem")
    fun find(item: @UnsafeVariance T): FoundItem<T>?

    @JsName("findPage")
    fun find(page: Int): Paged<T>?

    fun finalize()
}