package symphony.internal

import kase.Bag
import kase.Pending
import kollections.addAll
import kollections.emptyList
import kollections.buildList
import koncurrent.Later
import koncurrent.later.then
import koncurrent.later.andThen
import koncurrent.later.andZip
import koncurrent.later.zip
import koncurrent.later.catch
import symphony.Chunk
import symphony.GroupedPage
import symphony.GroupedPageLoader
import symphony.GroupedPageFindResult
import symphony.GroupedPaginationManager
import symphony.PageLoaderFunction
import symphony.Row
import symphony.internal.loaders.GroupedPageLoaderFinal
import symphony.internal.loaders.GroupedPageLoaderImpl
import symphony.internal.loaders.GroupedPageLoaderInitial
import symphony.internal.memory.GroupedPageMemoryManager

@PublishedApi
internal class GroupedPaginationManagerImpl<G, T>(
    capacity: Int
) : AbstractPaginationManager<T, GroupedPage<G, T>, GroupedPageFindResult<G, T>>(capacity), GroupedPaginationManager<G, T> {

    override val loader by lazy { Bag<GroupedPageLoader<G, T>>(GroupedPageLoaderInitial) }

    override val memory by lazy { GroupedPageMemoryManager<G, T>() }

    override val continuous get() = buildList<Chunk<G, Row<T>>> { forEachPage { page -> addAll(page.groups) } }

    override fun initialize(ld: PageLoaderFunction<Chunk<G, T>>): Later<GroupedPage<G, T>> {
        loader.value = GroupedPageLoaderImpl(ld)
        return loadFirstPage()
    }

    override fun forEachPage(block: (GroupedPage<G, T>) -> Unit) {
        memory.entries[capacity]?.pages?.values?.sortedBy { it.number }?.forEach(block)
    }

    override fun loadPage(no: Int): Later<GroupedPage<G, T>> {
        if (capacity <= 0) return Later(GroupedPage(emptyList(), 0, no))

        return load(page = no)
    }

    override fun deInitialize(clearPages: Boolean?) {
        if (clearPages != false) clearPages()
        current.value = Pending
        loader.value = GroupedPageLoaderFinal
    }
}