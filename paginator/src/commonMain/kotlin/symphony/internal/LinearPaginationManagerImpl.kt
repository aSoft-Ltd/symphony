package symphony.internal

import kase.Bag
import kase.Loading
import kase.Pending
import kase.Success
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import symphony.LinearPage
import symphony.LinearPageFindResult
import symphony.LinearPageLoader
import symphony.LinearPaginationManager
import symphony.PageLoaderFunction
import symphony.PageLoaderParams
import symphony.Row
import symphony.internal.loaders.LinearPageLoaderFinal
import symphony.internal.loaders.LinearPageLoaderImpl
import symphony.internal.loaders.LinearPageLoaderInitial
import symphony.internal.memory.LinearPageMemoryManager

@PublishedApi
internal class LinearPaginationManagerImpl<T>(
    capacity: Int,
) : AbstractPaginationManager<T, LinearPage<T>, LinearPageFindResult<T>>(capacity), LinearPaginationManager<T> {

    private val handler by lazy { Bag<suspend LinearPaginationManager<T>.(params: PageLoaderParams) -> Unit>({}) }
    override val loader by lazy { Bag<LinearPageLoader<T>>(LinearPageLoaderInitial) }

    override val memory by lazy { LinearPageMemoryManager<T>() }

    override val continuous
        get() = buildList<Row<T>> {
            forEachPage { page ->
                addAll(page.items.mapIndexed { index, row -> Row(pageCapacity = page.capacity, page.number, index * page.number, row.item) })
            }
        }

    @Deprecated("In favour of setup")
    override suspend fun initialize(pl: PageLoaderFunction<T>): LinearPage<T> {
        loader.value = LinearPageLoaderImpl(pl)
        search.value = null
        return loadFirstPage()
    }

    override suspend fun setup(loader: suspend LinearPaginationManager<T>.(params: PageLoaderParams) -> Unit): LinearPage<T> {
        handler.value = loader
        search.value = null
        return loadFirstPage()
    }

    override fun update(data: List<T>, loading: Boolean) {
        val items = data.mapIndexed { index, item -> Row(index, item) }
        val page = LinearPage(items, capacity.value, 1)
        current.value = if (loading) Loading("loading", page) else Success(page)
        memory.save(params.value, current.value.data ?: LinearPage(emptyList(), capacity.value, params.value.page))
    }

    override fun forEachPage(block: (LinearPage<T>) -> Unit) = memory.entries.values.forEach(block)

    override suspend fun loadPage(no: Int): LinearPage<T> {
        if (capacity.value <= 0) return LinearPage(emptyList(), 0, no)
        return load(page = no)
    }

    private var job: Job? = null
    override suspend fun load(page: Int): LinearPage<T> {
        job?.cancel()
        return coroutineScope {
            val p = params(page)
            params.value = p
            val memorizedPage = memory.load(p)
            current.value = Loading("Loading", memorizedPage)

            if (loader.value !is LinearPageLoaderInitial || loader.value !is LinearPageLoaderFinal) return@coroutineScope super.load(page)
            job = launch {
                val func = handler.value ?: throw IllegalStateException("setup your paginator first using paginator.setup { ... }")
                func(p)
            }
            job?.join()
            memory.save(p, current.value.data ?: LinearPage(emptyList(), capacity.value, page))
        }
    }

    override fun deInitialize(clearPages: Boolean?) {
        if (clearPages != false) clearPages()
        current.value = Pending
        loader.value = LinearPageLoaderFinal
    }
}