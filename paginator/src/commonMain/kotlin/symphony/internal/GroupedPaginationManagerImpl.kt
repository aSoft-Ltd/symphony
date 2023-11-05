package symphony.internal

import kase.Bag
import kase.Pending
import kollections.iEmptyList
import kollections.toIList
import koncurrent.Later
import symphony.Chunk
import symphony.GroupedPage
import symphony.GroupedPageLoader
import symphony.GroupedPageResult
import symphony.GroupedPaginationManager
import symphony.PageLoader
import symphony.internal.loaders.GroupedPageLoaderFinal
import symphony.internal.loaders.GroupedPageLoaderImpl
import symphony.internal.loaders.GroupedPageLoaderInitial
import symphony.internal.memory.GroupedPageMemoryManager

@PublishedApi
internal class GroupedPaginationManagerImpl<G, T>(
    capacity: Int
) : AbstractPaginationManager<T, GroupedPage<G, T>, GroupedPageResult<G, T>>(capacity), GroupedPaginationManager<G, T> {

    override val loader by lazy { Bag<GroupedPageLoader<G, T>>(GroupedPageLoaderInitial) }

    override val memory by lazy { GroupedPageMemoryManager<G, T>() }

    override val continuous get() = buildList { forEachPage { page -> addAll(page.groups) } }.toIList()

    override fun initialize(ld: PageLoader<Chunk<G, T>>): Later<GroupedPage<G, T>> {
        loader.value = GroupedPageLoaderImpl(ld)
        return loadFirstPage()
    }

    override fun forEachPage(block: (GroupedPage<G, T>) -> Unit) {
        memory.entries[capacity]?.pages?.values?.sortedBy { it.number }?.forEach(block)
    }

    override fun loadPage(no: Int): Later<GroupedPage<G, T>> {
        if (capacity <= 0) return Later(GroupedPage(iEmptyList(), 0, no))

        return load(page = no)
    }

    override fun deInitialize(clearPages: Boolean?) {
        if (clearPages != false) clearPages()
        current.value = Pending
        loader.value = GroupedPageLoaderFinal
    }
}