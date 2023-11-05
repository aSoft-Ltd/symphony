package symphony

import kollections.JsExport

@JsExport
data class GroupedPageResult<out G,out T>(
    val page: GroupedPage<G,T>,
    override val row: Row<T>
): IPageResult<T>