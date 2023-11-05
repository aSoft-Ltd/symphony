@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package symphony

import kollections.List
import koncurrent.Later
import kotlin.js.JsExport

interface LinearPaginationManager<out T> : IPaginationManager<T,LinearPage<T>,LinearPageResult<T>> {
    val continuous: List<Row<T>>

    fun initialize(pl: PageLoader<@UnsafeVariance T>): Later<LinearPage<T>>

    // --------------------- loopers ----------------------
    fun forEachPage(block: (LinearPage<T>) -> Unit)
}