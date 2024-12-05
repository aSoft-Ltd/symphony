package symphony.internal

import cinematic.MutableLive
import cinematic.mutableLiveOf
import kase.Bag
import kase.Failure
import kase.LazyState
import kase.Loading
import kase.Pending
import kase.Result
import kase.Success
import kase.toLazyState
import koncurrent.FailedLater
import koncurrent.Later
import koncurrent.later.andThen
import koncurrent.later.finally
import koncurrent.later.then
import symphony.AbstractPage
import symphony.PageFindResult
import symphony.PageLoader
import symphony.PageLoaderParams
import symphony.PageLoaderSource
import symphony.PaginationManager
import symphony.internal.memory.PageMemoryManager

@PublishedApi
internal abstract class AbstractPaginationManager<T, P : AbstractPage, R : PageFindResult<T>>(
    capacity: Int,
) : PaginationManager<T, P, R> {

    abstract val loader: Bag<PageLoader<P>>

    abstract val memory: PageMemoryManager<T, P, R>

    override val current: MutableLive<LazyState<P>> = mutableLiveOf(Pending)

    override val search = mutableLiveOf<String?>(null)

    override val capacity = mutableLiveOf(capacity)

    override val currentPageOrNull get() = current.value.data

    override val currentPageSize get() = currentPageOrNull?.size ?: 0

    override val hasMore: Boolean get() = currentPageOrNull?.hasMore == true

    override fun setSearchKey(key: String?) {
        search.value = key
    }

    override fun appendSearchKey(key: String?) {
        if (key == null) return
        val old = search.value ?: ""
        search.value = old + key
    }

    override fun backSpaceSearchKey() {
        val old = search.value ?: return
        if (old.isEmpty()) return
        search.value = old.dropLast(1)
    }

    override fun clearSearchKey() = setSearchKey(null)

    override fun wipeMemory() = memory.clear()

    override fun setPageCapacity(cap: Int) {
        capacity.value = cap
    }

    override fun clearPages() {
        wipeMemory()
        current.value = Pending
    }

    internal fun params(page: Int) = PageLoaderParams(page, capacity.value, search.value, sorter.data.value)
    protected fun load(page: Int): Later<P> {
        if (current.value is Loading) return FailedLater(LOADING_ERROR)

        val params = params(page)
        val memorizedPage = memory.load(params)
        current.value = Loading("Loading")
        println("Loading from LOCAL source....")
        return loader.getOrThrow().load(params, PageLoaderSource.LOCAL).then {  localRes->
            println("Loaded from local source....setting state, size=${localRes.size}")
            current.value = Success(localRes)
            memory.save(params, localRes)
            localRes
        }.then {  localRes->
            println("Loading from REMOTE source....")
            loader.getOrThrow().load(params, PageLoaderSource.REMOTE).then { remoteRes->
                println("Loaded from REMOTE source....setting state, size=${remoteRes.size}")
                current.value = Success(remoteRes)
                memory.save(params, remoteRes)
            }
            localRes
        }
//        return try {
//            println("Loading from local source....")
//            loader.getOrThrow().load(params, PageLoaderSource.LOCAL)
//        } catch (err: Throwable) {
//            FailedLater(err)
//        }.then {
//            memory.save(params, it)
//        }.finally {
//            println("Loaded from local source.. setting state")
//            current.value = it.toLazyState(memorizedPage)
//            println("Loading from remote source...")
//            loader.getOrThrow().load(params, PageLoaderSource.REMOTE).then {
//                println("Loaded from remote source: ${it}")
//                println("Setting state (R)")
//                current.value = Success(it)
//            }
//        }.then {
//            it
//        }
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

    override fun find(item: T) = memory.load(item)

    override fun find(row: Int, page: Int) = memory.load(row, params(page))

    override fun find(page: Int) = memory.load(params(page))

    companion object {
        val LOADING_ERROR = Throwable("Can't load page while paginator is still loading")
    }
}