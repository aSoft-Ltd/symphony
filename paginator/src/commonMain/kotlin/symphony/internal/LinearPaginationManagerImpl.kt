package symphony.internal

import kase.Bag
import symphony.Row
import kase.Pending
import symphony.LinearPage
import symphony.LinearPageLoader
import symphony.LinearPageFindResult
import symphony.LinearPaginationManager
import symphony.PageLoaderFunction
import symphony.internal.loaders.LinearPageLoaderFinal
import symphony.internal.loaders.LinearPageLoaderInitial
import symphony.internal.loaders.LinearPageLoaderImpl
import symphony.internal.memory.LinearPageMemoryManager

@PublishedApi
internal class LinearPaginationManagerImpl<T>(
    capacity: Int,
) : AbstractPaginationManager<T, LinearPage<T>, LinearPageFindResult<T>>(capacity), LinearPaginationManager<T> {

    override val loader by lazy { Bag<LinearPageLoader<T>>(LinearPageLoaderInitial) }

    override val memory by lazy { LinearPageMemoryManager<T>() }

    override val continuous
        get() = buildList<Row<T>> {
            forEachPage { page ->
                addAll(page.items.mapIndexed { index, row -> Row(pageCapacity = page.capacity, page.number, index * page.number, row.item) })
            }
        }

    override suspend fun initialize(pl: PageLoaderFunction<T>): LinearPage<T> {
        loader.value = LinearPageLoaderImpl(pl)
        search.value = null
        return loadFirstPage()
    }

    override fun forEachPage(block: (LinearPage<T>) -> Unit) = memory.entries.values.forEach(block)

    override suspend fun loadPage(no: Int): LinearPage<T> {
        if (capacity.value <= 0) return LinearPage(emptyList(), 0, no)
        return load(page = no)
    }

    override fun deInitialize(clearPages: Boolean?) {
        if (clearPages != false) clearPages()
        current.value = Pending
        loader.value = LinearPageLoaderFinal
    }
}