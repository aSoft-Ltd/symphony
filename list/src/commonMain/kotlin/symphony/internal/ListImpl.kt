package symphony.internal

import symphony.ActionsManager
import symphony.PaginationManager
import symphony.Row
import symphony.SelectionManager
import symphony.actionsOf
import kollections.List as KList
import symphony.List

@PublishedApi
internal class ListImpl<T>(
    override val paginator: PaginationManager<T>,
    override val selector: SelectionManager<T>,
    override val actions: ActionsManager<T>
) : List<T> {
    override val rows: KList<Row<T>> get() = paginator.continuous

    override fun manageActions(block: (ActionsManager<T>) -> Unit): ListImpl<T> {
        actions.apply(block)
        return this
    }

    override fun <R> map(transform: (T) -> R): ListImpl<R> {
        val p = paginator.map(transform)
        val s = SelectionManagerImpl(p)
        return ListImpl(p, s, actionsOf())
    }
}