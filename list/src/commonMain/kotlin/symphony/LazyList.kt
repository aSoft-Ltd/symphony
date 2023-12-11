@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.JsExport

interface LazyList<out T> {
    val paginator: PaginationManager<T, AbstractPage, PageFindResult<T>>
}