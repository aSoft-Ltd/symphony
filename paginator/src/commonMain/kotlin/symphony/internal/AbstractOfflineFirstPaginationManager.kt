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
import kollections.firstOrNull
import koncurrent.FailedLater
import koncurrent.Later
import koncurrent.later.andThen
import koncurrent.later.catch
import koncurrent.later.finally
import koncurrent.later.then
import symphony.AbstractPage
import symphony.LinearPage
import symphony.PageFindResult
import symphony.PageLoader
import symphony.PageLoaderParams
import symphony.PageLoaderSource
import symphony.PaginationManager
import symphony.internal.memory.PageMemoryManager
import kotlin.random.Random
import kotlin.time.TimeSource

@PublishedApi
internal abstract class AbstractOfflineFirstPaginationManager<T, P : AbstractPage, R : PageFindResult<T>>(
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
    private var lastRequestedPage:Int = 1
    private var latestRequestTime = generateUUID()

    fun generateUUID(): String {
        val chars = ('a'..'f') + ('0'..'9')
        val random = Random
        val uuid = StringBuilder(32)
        for (i in 0 until 32) {
            when (i) {
                8, 12, 16, 20 -> uuid.append('-')
                else -> uuid.append(chars[random.nextInt(chars.size)])
            }
        }
        return uuid.toString()
    }

    protected fun load(page: Int): Later<Unit> {
//        if (current.value is Loading) return FailedLater(LOADING_ERROR)
        val currentMillis = TimeSource.Monotonic.markNow().elapsedNow().inWholeMilliseconds
        val requestTime = generateUUID()
        latestRequestTime = requestTime
        println(currentMillis)

        lastRequestedPage = page
        val params = params(page)
        val memorizedPage = memory.load(params)
        current.value = Loading("Loading")
//        println("Loading from LOCAL source....page=${page}")
        val ret =  loader.getOrThrow().load(params, PageLoaderSource.LOCAL).then {  localRes->
//            println("Loaded from local source,page=${page}....setting state, size=${localRes.size}")
            if (requestTime == latestRequestTime) {
                current.value = Success(localRes)
                memory.save(params, localRes)
            }


//            localRes
        }.then {  localRes->
//            println("Loading from REMOTE source,page=${page}....")
            loader.getOrThrow().load(params, PageLoaderSource.REMOTE).then { remoteRes->
//                println("Loaded from REMOTE source....setting state,page=${page}, size=${remoteRes.size}")
                if (requestTime == latestRequestTime) {
                    current.value = Success(remoteRes)
                    memory.save(params, remoteRes)
                }

            }.catch {
                println("remote error: ${it.message}")
                localRes
            }
            Unit
//            localRes
        }
        return ret
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