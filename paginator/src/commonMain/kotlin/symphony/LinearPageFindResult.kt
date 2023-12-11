package symphony

import kotlinx.JsExport

@JsExport
data class LinearPageFindResult<out T>(
    override val page: LinearPage<T>,
    override val row: Row<T>
): PageFindResult<T>