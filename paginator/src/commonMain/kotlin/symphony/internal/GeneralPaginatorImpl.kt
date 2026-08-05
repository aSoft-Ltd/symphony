package symphony.internal

import cinematic.mutableLiveOf
import symphony.Cursor
import symphony.CursorPaged
import symphony.CursorPaginationParams
import symphony.FoundItem
import symphony.OffsetPaged
import symphony.OffsetPaginationParams
import symphony.Paged
import symphony.PaginationParams
import symphony.Paginator
import symphony.Row
import kotlin.math.max

@PublishedApi
internal class GeneralPaginatorImpl<T>(
    capacity: Int,
) : Paginator<T> {

    private var loader: suspend Paginator<T>.(params: PaginationParams) -> Unit = { println("Paginator is not initialized") }
    override val params by lazy { mutableLiveOf<PaginationParams>(CursorPaginationParams(Cursor.First, capacity)) }

    private val pages by lazy { mutableMapOf<Int, Paged<T>>() }

    override val state by lazy { mutableLiveOf<Paged<T>>(CursorPaged.empty(capacity)) }

    override fun setPageCapacity(cap: Int) {
        params.value = params.value.with(capacity = cap)
    }

    override suspend fun initialize(block: suspend Paginator<T>.(params: PaginationParams) -> Unit): Paged<T> {
        loader = block
        return loadFirstPage()
    }

    private suspend fun load(pp: PaginationParams): Paged<T> {
        params.value = pp
        loader(pp)
        val paged = state.value
        pages[paged.page] = paged
        return paged
    }

    private suspend fun load(body: () -> PaginationParams): Paged<T> = load(body())

    override suspend fun refresh(): Paged<T> = load(params.value)

    override suspend fun loadNextPage(): Paged<T> = load {
        when (val s = state.value) {
            is CursorPaged<*> -> CursorPaginationParams(Cursor(reference = s.cursor.next, direction = Cursor.Direction.Forward), params.value.capacity)
            is OffsetPaged<*> -> OffsetPaginationParams(s.page, params.value.capacity)
        }
    }

    override suspend fun loadPreviousPage(): Paged<T> = load {
        when (val s = state.value) {
            is CursorPaged<*> -> CursorPaginationParams(Cursor(reference = s.cursor.prev, direction = Cursor.Direction.Backward), params.value.capacity)
            is OffsetPaged<*> -> OffsetPaginationParams(max(0, s.page - 2), params.value.capacity)
        }
    }

    override suspend fun loadFirstPage(): Paged<T> = load(page = 1)

    override fun find(row: Int, page: Int): FoundItem<T>? {
        val p = pages[page] ?: return null
        return find(row, p)
    }

    private fun find(row: Int, paged: Paged<T>): FoundItem<T>? {
        val it = paged.items.content.mapIndexed { idx, t -> Row(idx, t) }.find { it.number == row } ?: return null
        return FoundItem(it.item, paged)
    }

    override fun find(item: T): FoundItem<T>? {
        val paged = state.value
        val it = paged.items.content.find { it == item }
        if (it != null) return FoundItem(it, paged)
        for (p in pages.values) {
            val it = p.items.content.find { it == item }
            if (it != null) return FoundItem(it, p)
        }
        return null
    }

    override fun find(page: Int): Paged<T>? {
        if (state.value.page == page) return state.value
        return pages[page]
    }

    override fun update(data: Paged<T>) {
        pages[data.page] = data
        state.value = data
    }

    override suspend fun load(page: Int): Paged<T> = load {
        when (page) {
            1 -> CursorPaginationParams(Cursor.First, params.value.capacity)
            -1 -> CursorPaginationParams(Cursor.Last, params.value.capacity)
            else -> OffsetPaginationParams(page - 1, params.value.capacity)
        }
    }


    override fun finalize() {
        params.value = PaginationParams.first(params.value.capacity)
        state.value = CursorPaged.empty(params.value.capacity)
        pages.clear()
        loader = { println("Paginator is already finalized") }
    }
}