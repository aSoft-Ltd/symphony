package symphony

class PaginatorState<out T>(
    val number: PageNumber,
    val capacity: Int,
    val params: PaginationParams = OffsetPaginationParams(number.current - 1, capacity),
    val data: PagedData<T> = PagedOffsetData.empty(params)
)