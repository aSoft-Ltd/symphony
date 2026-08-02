package symphony

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class Persons(
    private val total: Int = 10,
    private val duration: Duration = 500.milliseconds
) {
    private val Total by lazy { Person.generate(total) }

    suspend fun all(params: PaginationParams): PagedData<Person> = withContext(Dispatchers.Default) {
        delay(duration)
        when (params) {
            is CursorPaginationParams -> all(params)
            is OffsetPaginationParams -> all(params)
        }
    }

    fun all(params: OffsetPaginationParams): PagedOffsetData<Person> {
        val sorted = Total.sortedBy { it.uid }
        val skips = params.offset * params.capacity
        val items = sorted.drop(skips).take(params.capacity + 1)
        val content = if (items.size > params.capacity) items.dropLast(1) else items
        return PagedOffsetData(
            params = params,
            number = PageNumber(params.offset + 1, Total.size),
            content = content,
            capacity = params.capacity
        )
    }

    fun all(params: CursorPaginationParams): PagedCursorData<Person> {
        val sorted = Total.sortedBy { it.uid }
        val cursor = params.cursor.reference
        return when (params.cursor.direction) {
            Cursor.Direction.Forward -> {
                val filtered = if (cursor != null) sorted.filter { it.uid > cursor } else sorted
                val items = filtered.take(params.capacity + 1)
                val content = if (items.size > params.capacity) items.dropLast(1) else items
                val currentPage = if (cursor == null) 1 else (sorted.indexOfFirst { it.uid == cursor } + 1) / params.capacity + 1
                val pageNumber = PageNumber(currentPage, Total.size)
                PagedCursorData(
                    params = params,
                    content = content,
                    number = pageNumber,
                    cursor = PagedCursorData.Cursor(
                        next = if (items.size > params.capacity) content.last().uid else null,
                        prev = if (cursor != null) content.firstOrNull()?.uid else null
                    ),
                    capacity = params.capacity
                )
            }

            Cursor.Direction.Backward -> {
                val filtered = if (cursor != null) sorted.filter { it.uid < cursor } else sorted
                val items = if (filtered.size > params.capacity) filtered.takeLast(params.capacity + 1) else filtered
                val content = if (items.size > params.capacity) items.drop(1) else items
                val currentPage = if (cursor == null) (Total.size + params.capacity - 1) / params.capacity else sorted.indexOfFirst { it.uid == cursor } / params.capacity
                val pageNumber = PageNumber(currentPage, Total.size)
                PagedCursorData(
                    params = params,
                    content = content,
                    number = pageNumber,
                    cursor = PagedCursorData.Cursor(
                        next = content.lastOrNull()?.uid,
                        prev = if (items.size > params.capacity) content.firstOrNull()?.uid else null
                    ),
                    capacity = params.capacity
                )
            }
        }
    }
}