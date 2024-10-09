@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.Live
import kase.LazyState
import koncurrent.Later
import kotlinx.JsExport
import kotlin.js.JsName

interface PaginationManager<out T, out P : Page, out R : PageFindResult<T>> {
    val current: Live<LazyState<P>>
    val search: Live<String?>
    val currentPageOrNull: P?
    val currentPageSize : Int
    val capacity: Live<Int>
    val hasMore: Boolean
    val sorter: ColumnSorter

    fun wipeMemory()
    fun clearPages()
    fun setPageCapacity(cap: Int)
    // --------------------- searchers ---------------------
    fun setSearchKey(key: String?)

    fun appendSearchKey(key: String?)

    fun backSpaceSearchKey()

    fun clearSearchKey()

    // --------------------- loaders ---------------
    fun refreshAllPages(): Later<Any?>
    fun refreshCurrentPage(): Later<Any?>
    fun loadNextPage(): Later<Any?>
    fun loadPreviousPage(): Later<Any?>
    fun loadPage(no: Int): Later<P>
    fun loadFirstPage(): Later<P>
    fun loadLastPage(): Later<P>

    // ---------------------- finders -----------------------
    @JsName("findRow")
    fun find(row: Int, page: Int): R?

    @JsName("findItem")
    fun find(item: @UnsafeVariance T): R?

    @JsName("findPage")
    fun find(page: Int): P?

    fun deInitialize(clearPages: Boolean? = false)
}