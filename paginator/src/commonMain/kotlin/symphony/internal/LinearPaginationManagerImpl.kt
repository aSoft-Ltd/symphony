package symphony.internal

import kollections.toIList
import koncurrent.Later
import kase.Bag
import symphony.Row
import kase.Pending
import kollections.iEmptyList
import symphony.LinearPage
import symphony.LinearPageLoader
import symphony.LinearPageFindResult
import symphony.LinearPaginationManager
import symphony.PageLoader
import symphony.internal.loaders.LinearPageLoaderFinal
import symphony.internal.loaders.LinearPageLoaderInitial
import symphony.internal.loaders.LinearPageLoaderImpl
import symphony.internal.memory.LinearPageMemoryManager

@PublishedApi
internal class LinearPaginationManagerImpl<T>(
    capacity: Int
) : AbstractPaginationManager<T, LinearPage<T>, LinearPageFindResult<T>>(capacity), LinearPaginationManager<T> {

    override val loader by lazy { Bag<LinearPageLoader<T>>(LinearPageLoaderInitial) }

    override val memory by lazy { LinearPageMemoryManager<T>() }

    override val continuous
        get() = buildList {
            forEachPage { page ->
                addAll(page.items.mapIndexed { index, row -> Row(pageCapacity = page.capacity, page.number, index * page.number, row.item) })
            }
        }.toIList()

    override fun initialize(pl: PageLoader<T>): Later<LinearPage<T>> {
        loader.value = LinearPageLoaderImpl(pl)
        return loadFirstPage()
    }

    override fun forEachPage(block: (LinearPage<T>) -> Unit) {
        memory.entries[capacity]?.pages?.values?.sortedBy { it.number }?.forEach(block)
    }

    override fun loadPage(no: Int): Later<LinearPage<T>> {
        if (capacity <= 0) return Later(LinearPage(iEmptyList(), 0, no))
        return load(page = no)
    }

    override fun deInitialize(clearPages: Boolean?) {
        if (clearPages != false) clearPages()
        current.value = Pending
        loader.value = LinearPageLoaderFinal
    }
}