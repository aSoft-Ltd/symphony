package symphony

import kotlinx.JsExport

@JsExport
data class GroupedPageFindResult<out G,out T>(
    override val page: GroupedPage<G,T>,
    override val row: Row<T>
): PageFindResult<T>