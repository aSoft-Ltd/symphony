package symphony.internal

import cinematic.mutableLiveOf
import symphony.Cursor
import symphony.CursorPaginationParams
import symphony.FoundItem
import symphony.OffsetPaginationParams
import symphony.CursorPaged
import symphony.Paged
import symphony.OffsetPaged
import symphony.PaginationParams
import symphony.Paginator
import kotlin.math.max

@PublishedApi
internal class GeneralPaginatorImpl<T>(
    capacity: Int,
) : Paginator<T> {

    private var loader: suspend Paginator<T>.(params: PaginationParams) -> Unit = { println("Paginator is not initialized") }
    override val params by lazy { mutableLiveOf<PaginationParams>(CursorPaginationParams(Cursor.First, capacity)) }
    override val state by lazy { mutableLiveOf<Paged<T>>(CursorPaged.empty(capacity)) }

    override fun setPageCapacity(cap: Int) {
        params.value = params.value.with(capacity = cap)
    }

    override suspend fun initialize(block: suspend Paginator<T>.(params: PaginationParams) -> Unit): Paged<T> {
        loader = block
        return loadFirstPage()
    }

    override suspend fun refresh(): Paged<T> {
        loader(params.value)
        return state.value
    }

    override suspend fun loadNextPage(): Paged<T> {
        val p = when (val s = state.value) {
            is CursorPaged<*> -> CursorPaginationParams(Cursor(reference = s.cursor.next, direction = Cursor.Direction.Forward), params.value.capacity)
            is OffsetPaged<*> -> OffsetPaginationParams(s.page, params.value.capacity)
        }
        params.value = p
        loader(p)
        return state.value
    }

    override suspend fun loadPreviousPage(): Paged<T> {
        val p = when (val s = state.value) {
            is CursorPaged<*> -> CursorPaginationParams(Cursor(reference = s.cursor.prev, direction = Cursor.Direction.Backward), params.value.capacity)
            is OffsetPaged<*> -> OffsetPaginationParams(max(0, s.page - 2), params.value.capacity)
        }
        params.value = p
        loader(p)
        return state.value
    }

    override suspend fun loadFirstPage(): Paged<T> = load(page = 1)

    override fun find(item: T): FoundItem<T>? {
        TODO("Not yet implemented")
    }

    override fun find(page: Int): Paged<T>? {
        TODO("Not yet implemented")
    }

    override fun update(data: Paged<T>) {
        state.value = data
    }

    override suspend fun load(page: Int): Paged<T> {
        val p = when (page) {
            1 -> CursorPaginationParams(Cursor.First, params.value.capacity)
            -1 -> CursorPaginationParams(Cursor.Last, params.value.capacity)
            else -> OffsetPaginationParams(page - 1, params.value.capacity)
        }
        params.value = p
        loader(p)
        return state.value
    }

    override fun finalize() {
        params.value = PaginationParams.first(params.value.capacity)
        state.value = CursorPaged.empty(params.value.capacity)
        loader = { println("Paginator is already finalized") }
    }
}