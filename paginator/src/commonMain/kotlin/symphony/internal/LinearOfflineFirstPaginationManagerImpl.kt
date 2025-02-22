package symphony.internal

import koncurrent.Later
import koncurrent.later.then
import koncurrent.later.andThen
import koncurrent.later.andZip
import koncurrent.later.zip
import koncurrent.later.catch
import kase.Bag
import symphony.Row
import kase.Pending
import kollections.addAll
import kollections.emptyList
import kollections.buildList
import kollections.mapIndexed
import koncurrent.SuccessfulLater
import symphony.ColumnSorter
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
internal class LinearOfflineFirstPaginationManagerImpl<T>(
    capacity: Int,
) : AbstractOfflineFirstPaginationManager<T, LinearPage<T>, LinearPageFindResult<T>>(capacity), LinearPaginationManager<T> {

    override val loader by lazy { Bag<LinearPageLoader<T>>(LinearPageLoaderInitial) }

    override val memory by lazy { LinearPageMemoryManager<T>() }
    override val sorter: ColumnSorter = ColumnSorter {

    }
    override val continuous
        get() = buildList<Row<T>> {
            forEachPage { page ->
                addAll(page.items.mapIndexed { index, row -> Row(pageCapacity = page.capacity, page.number, index * page.number, row.item) })
            }
        }

    override fun initialize(pl: PageLoaderFunction<T>): Later<Any?> {
        loader.value = LinearPageLoaderImpl(pl)
        search.value = null
        return loadFirstPage()
    }

    override fun forEachPage(block: (LinearPage<T>) -> Unit) = memory.entries.values.forEach(block)

    override fun loadPage(no: Int): Later<Unit> {
//        if (capacity.value <= 0) return Later(LinearPage(emptyList(), 0, no))
        if (capacity.value <= 0) SuccessfulLater(Unit)
        return load(page = no)
    }

    override fun deInitialize(clearPages: Boolean?) {
        if (clearPages != false) clearPages()
        current.value = Pending
        loader.value = LinearPageLoaderFinal
    }
}