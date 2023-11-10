package symphony.internal

import cinematic.MutableLive
import cinematic.mutableLiveOf
import kase.Bag
import kase.Failure
import kase.LazyState
import kase.Loading
import kase.Pending
import kase.Success
import kase.toLazyState
import koncurrent.FailedLater
import koncurrent.Later
import koncurrent.later.finally
import symphony.AbstractPage
import symphony.PageLoader
import symphony.PageFindResult
import symphony.PaginationManager
import symphony.internal.memory.PageMemoryManager

@PublishedApi
internal abstract class AbstractPaginationManager<T, P : AbstractPage, R : PageFindResult<T>>(
    override var capacity: Int
) : PaginationManager<T, P, R> {

    abstract val loader: Bag<PageLoader<P>>

    abstract val memory: PageMemoryManager<T, P, R>

    override val current: MutableLive<LazyState<P>> = mutableLiveOf(Pending)

    override val currentPageOrNull get() = current.value.data

    override val currentPageSize get() = currentPageOrNull?.size ?: 0

    override val hasMore: Boolean get() = currentPageOrNull?.hasMore == true

    override fun wipeMemory() = memory.clear()

    override fun setPageCapacity(cap: Int) {
        capacity = cap
    }

    override fun clearPages() {
        wipeMemory()
        current.value = Pending
    }

    protected fun load(page: Int): Later<P> {
        if (current.value is Loading) return FailedLater(LOADING_ERROR)

        val memorizedPage = memory.load(page, capacity)
        current.value = Loading("Loading", memorizedPage)
        return try {
            loader.getOrThrow().load(page, capacity)
        } catch (err: Throwable) {
            FailedLater(err)
        }.then {
            memory.save(it)
        }.finally {
            current.value = it.toLazyState(memorizedPage)
        }
    }

    override fun loadNextPage() = when (val state = current.value) {
        is Pending -> loadPage(1)
        is Loading -> Later(Unit)
        is Failure -> loadPage(1)
        is Success -> when {
            state.data.isEmpty -> Later(state.data)
            state.data.isLastPage -> Later(state.data)
            else -> loadPage(state.data.number + 1)
        }
    }

    override fun loadPreviousPage() = when (val state = current.value) {
        is Pending -> loadPage(1)
        is Loading -> Later(Unit)
        is Failure -> loadPage(1)
        is Success -> when {
            state.data.number > 1 -> loadPage(state.data.number - 1)
            else -> loadPage(1)
        }
    }

    override fun refreshAllPages(): Later<Any?> {
        clearPages()
        return loadFirstPage()
    }

    override fun refreshCurrentPage() = when (val state = current.value) {
        is Pending -> loadPage(1)
        is Loading -> Later(Unit)
        is Failure -> loadPage(1)
        is Success -> loadPage(state.data.number)
    }

    private fun loadNothing() = Later(Unit)

    override fun loadFirstPage() = loadPage(1)

    override fun loadLastPage() = loadPage(-1)

    override fun find(item: T) = memory.load(item, capacity)

    override fun find(row: Int, page: Int) = memory.load(row, page, capacity)

    override fun find(page: Int) = memory.load(page, capacity)

    companion object {
        val LOADING_ERROR = Throwable("Can't load page while paginator is still loading")
    }
}