package symphony

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class Persons(
    private val total: Int = 10,
    private val duration: Duration = 50.milliseconds
) {
    private val Total by lazy { Person.generate(total) }

    suspend fun all(params: PaginationParams): Paged<Person> = withContext(Dispatchers.Default) {
        delay(duration)
        when (params) {
            is CursorPaginationParams -> all(params)
            is OffsetPaginationParams -> all(params)
        }
    }

    fun all(params: OffsetPaginationParams): OffsetPaged<Person> {
        val sorted = Total.sortedBy { it.uid }
        val skips = params.offset * params.capacity
        val items = sorted.drop(skips).take(params.capacity + 1)
        val content = if (items.size > params.capacity) items.dropLast(1) else items
        return OffsetPaged(
            page = params.offset + 1,
            items = Items(
                content = content,
                capacity = params.capacity,
                total = Total.size,
            )
        )
    }

    fun all(params: CursorPaginationParams): CursorPaged<Person> {
        val sorted = Total.sortedBy { it.uid }
        val cursor = params.cursor.reference
        return when (params.cursor.direction) {
            Cursor.Direction.Forward -> {
                val filtered = if (cursor != null) sorted.filter { it.uid > cursor } else sorted
                val items = filtered.take(params.capacity + 1)
                val content = if (items.size > params.capacity) items.dropLast(1) else items
                val currentPage = if (cursor == null) 1 else (sorted.indexOfFirst { it.uid == cursor } + 1) / params.capacity + 1
                CursorPaged(
                    page = currentPage,
                    items = Items(
                        content = content,
                        capacity = params.capacity,
                        total = Total.size,
                    ),
                    cursor = CursorPaged.Cursor(
                        next = if (items.size > params.capacity) content.last().uid else null,
                        prev = if (cursor != null) content.firstOrNull()?.uid else null
                    ),
                )
            }

            Cursor.Direction.Backward -> {
                val filtered = if (cursor != null) sorted.filter { it.uid < cursor } else sorted
                val items = if (filtered.size > params.capacity) filtered.takeLast(params.capacity + 1) else filtered
                val content = if (items.size > params.capacity) items.drop(1) else items
                val currentPage = if (cursor == null) (Total.size + params.capacity - 1) / params.capacity else sorted.indexOfFirst { it.uid == cursor } / params.capacity
                CursorPaged(
                    items = Items(
                        content = content,
                        capacity = params.capacity,
                        total = Total.size,
                    ),
                    page = currentPage,
                    cursor = CursorPaged.Cursor(
                        next = content.lastOrNull()?.uid,
                        prev = if (items.size > params.capacity) content.firstOrNull()?.uid else null
                    ),
                )
            }
        }
    }
}