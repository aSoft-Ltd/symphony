package symphony.internal

import symphony.SelectorBasedActionsManager
import symphony.ColumnsManager
import symphony.PaginationManager
import symphony.SelectionManager
import symphony.Table
import symphony.actionsOf
import symphony.columnsOf

@PublishedApi
internal class TableImpl<T>(
    override val paginator: PaginationManager<T>,
    override val selector: SelectionManager<T>,
    override val actions: SelectorBasedActionsManager<T>,
    override val columns: ColumnsManager<T>
) : Table<T> {

    override fun manageColumns(block: (ColumnsManager<T>) -> Unit): TableImpl<T> {
        columns.apply(block)
        return this
    }

    override fun manageActions(block: (SelectorBasedActionsManager<T>) -> Unit): TableImpl<T> {
        actions.apply(block)
        return this
    }

    override fun <R> map(transform: (T) -> R): TableImpl<R> {
        val p = paginator.map(transform)
        val s = SelectionManagerImpl(p)
        return TableImpl(p, s, actionsOf(), columnsOf())
    }
}