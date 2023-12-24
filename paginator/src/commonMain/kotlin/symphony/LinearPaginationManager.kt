@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package symphony

import kollections.List
import koncurrent.Later
import koncurrent.later.then
import koncurrent.later.andThen
import koncurrent.later.andZip
import koncurrent.later.zip
import koncurrent.later.catch
import kotlinx.JsExport

interface LinearPaginationManager<out T> : PaginationManager<T, LinearPage<T>, LinearPageFindResult<T>> {
    val continuous: List<Row<T>>

    fun initialize(pl: PageLoaderFunction<@UnsafeVariance T>): Later<LinearPage<T>>

    // --------------------- loopers ----------------------
    fun forEachPage(block: (LinearPage<T>) -> Unit)
}