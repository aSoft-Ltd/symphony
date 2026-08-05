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

    suspend fun initialize(block: suspend Paginator<@UnsafeVariance T>.(params: PaginationParams) -> Unit) : Paged<T>

    // --------------------- loaders ---------------
    suspend fun refresh(): Paged<T>
    suspend fun loadNextPage(): Paged<T>
    suspend fun loadPreviousPage(): Paged<T>
    suspend fun load(page: Int): Paged<T>
    suspend fun loadFirstPage(): Paged<T> = load(page = 1)
    suspend fun loadLastPage(): Paged<T> = load(page = -1)

    // ---------------------- finders -----------------------
    @JsName("findRow")
    fun find(row: Int, page: Int): FoundItem<T>?

    @JsName("findItem")
    fun find(item: @UnsafeVariance T): FoundItem<T>?

    @JsName("findPage")
    fun find(page: Int): Paged<T>?

    fun finalize()
}