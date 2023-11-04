package symphony.internal

import symphony.SelectorBasedActionsManager
import symphony.PaginationManager
import symphony.Row
import symphony.SelectionManager
import symphony.actionsOf
import kollections.List as KList
import symphony.LazyList

@PublishedApi
internal class LazyListImpl<T>(
    override val paginator: PaginationManager<T>,
    override val selector: SelectionManager<T>,
    override val actions: SelectorBasedActionsManager<T>
) : LazyList<T> {
    override val rows: KList<Row<T>> get() = paginator.continuous

    override fun manageActions(block: (SelectorBasedActionsManager<T>) -> Unit): LazyListImpl<T> {
        actions.apply(block)
        return this
    }

    override fun <R> map(transform: (T) -> R): LazyListImpl<R> {
        val p = paginator.map(transform)
        val s = SelectionManagerImpl(p)
        return LazyListImpl(p, s, actionsOf())
    }
}