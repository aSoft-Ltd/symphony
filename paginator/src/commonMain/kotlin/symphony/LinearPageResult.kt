package symphony

import kollections.JsExport

@JsExport
data class LinearPageResult<out T>(
    val page: LinearPage<T>,
    override val row: Row<T>
): IPageResult<T>