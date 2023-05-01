package symphony

import kollections.JsExport

@JsExport
data class PageResult<out T>(
    val page: Page<T>,
    val row: Row<T>
)