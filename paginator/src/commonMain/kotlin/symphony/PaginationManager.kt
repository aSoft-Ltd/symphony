@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.Live
import kase.LazyState
import koncurrent.Later
import koncurrent.later.then
import koncurrent.later.andThen
import koncurrent.later.andZip
import koncurrent.later.zip
import koncurrent.later.catch
import kotlinx.JsExport
import kotlin.js.JsName

interface PaginationManager<out T, out P : AbstractPage, out R : PageFindResult<T>> {
    val current: Live<LazyState<P>>
    val currentPageOrNull: P?
    val currentPageSize : Int
    val capacity: Int
    val hasMore: Boolean

    fun wipeMemory()
    fun clearPages()
    fun setPageCapacity(cap: Int)

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