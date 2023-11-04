package symphony.internal

import kollections.List
import symphony.Chunk
import symphony.GroupedList
import symphony.PaginationManager
import symphony.SelectionManager
import symphony.SelectorBasedActionsManager

@PublishedApi
internal class GroupedListImpl<G, T>(
    override val paginator: PaginationManager<T>,
    private val grouper: (List<T>) -> List<Chunk<G, T>>,
    override val selector: SelectionManager<T>,
    override val actions: SelectorBasedActionsManager<T>
) : GroupedList<G, T> {

    override val groups: List<Chunk<G, T>> get() = paginator.continuous.map { it.item }.let { grouper(it) }

    override fun manageActions(block: (SelectorBasedActionsManager<T>) -> Unit): GroupedListImpl<G, T> {
        actions.apply(block)
        return this
    }
}