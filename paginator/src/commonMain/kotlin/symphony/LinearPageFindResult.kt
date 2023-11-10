package symphony

import kollections.JsExport

@JsExport
data class LinearPageFindResult<out T>(
    override val page: LinearPage<T>,
    override val row: Row<T>
): PageFindResult<T>