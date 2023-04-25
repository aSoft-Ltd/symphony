@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport

interface Pageable<T> {
    val paginator: PaginationManager<T>
}