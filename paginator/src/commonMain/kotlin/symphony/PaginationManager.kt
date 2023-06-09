@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package symphony

import kase.LazyState
import kollections.List
import koncurrent.Later
import cinematic.Live
import kotlin.js.JsExport
import kotlin.js.JsName

interface PaginationManager<T> {
    val current: Live<LazyState<Page<T>>>
    val continuous: List<Row<T>>
    val currentPageOrNull: Page<T>?
    val capacity: Int

    val hasMore: Boolean

    fun initialize(loader: PageLoader<T>? = null): Later<Page<T>>

    fun wipeMemory()
    fun clearPages()
    fun setPageCapacity(cap: Int)

    // --------------------- loaders ---------------
    fun refreshAllPages(): Later<Any?>
    fun refreshCurrentPage(): Later<Any?>
    fun loadNextPage(): Later<Any?>
    fun loadPreviousPage(): Later<Any?>
    fun loadPage(no: Int): Later<Page<T>>
    fun loadFirstPage(): Later<Page<T>>
    fun loadLastPage(): Later<Page<T>>

    // --------------------- loopers ----------------------
    fun forEachPage(block: (Page<T>) -> Unit)

    // ---------------------- finders -----------------------
    @JsName("findRow")
    fun find(row: Int, page: Int): PageResult<T>?

    @JsName("findItem")
    fun find(item: T): PageResult<T>?

    @JsName("findPage")
    fun find(page: Int): Page<T>?

    fun <R> map(transform: (T) -> R): PaginationManager<R>

    fun deInitialize(clearPages: Boolean? = false)
}