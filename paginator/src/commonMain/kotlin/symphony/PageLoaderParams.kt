package symphony

data class PageLoaderParams(
    val page: Int,
    val limit: Int,
    val key: String?,
    val sort: ColumnSort?
)