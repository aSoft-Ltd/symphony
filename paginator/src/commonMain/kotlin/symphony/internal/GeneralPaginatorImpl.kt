package symphony.internal

import cinematic.mutableLiveOf
import symphony.BackwardCursor
import symphony.CursorPaginationParams
import symphony.ForwardCursor
import symphony.OffsetPaginationParams
import symphony.PagedCursorData
import symphony.PagedData
import symphony.PagedOffsetData
import symphony.PaginationParams
import symphony.Paginator
import kotlin.math.max

@PublishedApi
internal class GeneralPaginatorImpl<T>(
    capacity: Int,
) : Paginator<T> {

    private var loader: suspend Paginator<T>.(params: PaginationParams) -> Unit = { println("Paginator is not initialized") }

    override val state by lazy { mutableLiveOf<PagedData<T>>(PagedCursorData.empty(capacity)) }

    override fun setPageCapacity(cap: Int) {
        state.value = state.value.with(capacity = cap)
    }

    override suspend fun initialize(block: suspend Paginator<T>.(params: PaginationParams) -> Unit): PagedData<T> {
        loader = block
        return loadFirstPage()
    }

    override suspend fun refresh(): PagedData<T> {
        loader(state.value.params.with(state.value))
        return state.value
    }

    override suspend fun loadNextPage(): PagedData<T> {
        val params = when (val s = state.value) {
            is PagedCursorData<*> -> CursorPaginationParams(ForwardCursor(s.cursor.next), state.value.capacity)
            is PagedOffsetData<*> -> OffsetPaginationParams(s.number.current, state.value.capacity)
        }
        loader(params)
        return state.value
    }

    override suspend fun loadPreviousPage(): PagedData<T> {
        val params = when (val s = state.value) {
            is PagedCursorData<*> -> CursorPaginationParams(BackwardCursor(s.cursor.prev), state.value.capacity)
            is PagedOffsetData<*> -> OffsetPaginationParams(max(1, s.number.current - 2), state.value.capacity)
        }
        loader(params)
        return state.value
    }

    override suspend fun loadFirstPage(): PagedData<T> = load(page = 1)
//    override fun find(row: Int, page: Int): LinearPageFindResult<T>? {
//        TODO("Not yet implemented")
//    }
//
//    override fun find(item: T): LinearPageFindResult<T>? {
//        TODO("Not yet implemented")
//    }

    override fun find(page: Int): PagedData<T>? {
        TODO("Not yet implemented")
    }

    override fun update(data: PagedData<T>) {
        state.value = data
    }

    override suspend fun load(page: Int): PagedData<T> {
        val params = when (page) {
            1 -> CursorPaginationParams(ForwardCursor.First, state.value.capacity)
            -1 -> CursorPaginationParams(BackwardCursor.Last, state.value.capacity)
            else -> OffsetPaginationParams(page - 1, state.value.capacity)
        }
        loader(params)
        return state.value
    }

    override fun finalize() {
        loader = { println("Paginator is already finalized") }
    }
}