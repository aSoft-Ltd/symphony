package symphony

import kollections.JsExport

@Deprecated("In favour of LinearPageResult")
@JsExport
data class PageResult<out T>(
    val page: Page<T>,
    val row: Row<T>
)