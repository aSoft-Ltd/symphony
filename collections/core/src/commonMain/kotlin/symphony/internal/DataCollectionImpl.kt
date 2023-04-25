package symphony.internal

import kollections.List
import symphony.ActionsManager
import symphony.ColumnsManager
import symphony.DataCollection
import symphony.PaginationManager
import symphony.Row
import symphony.ScrollableList
import symphony.SelectionManager
import symphony.Table
import symphony.actionsOf
import symphony.columnsOf

@PublishedApi
internal class DataCollectionImpl<T>(
    override val paginator: PaginationManager<T>,
    override val selector: SelectionManager<T>,
    override val actions: ActionsManager<T>,
    override val columns: ColumnsManager<T>
) : Table<T>, ScrollableList<T>, DataCollection<T> {

    override val rows: List<Row<T>> get() = paginator.continuous

    override fun manageColumns(block: (ColumnsManager<T>) -> Unit): DataCollectionImpl<T> {
        columns.apply(block)
        return this
    }

    override fun manageActions(block: (ActionsManager<T>) -> Unit): DataCollectionImpl<T> {
        actions.apply(block)
        return this
    }

    override fun <R> map(transform: (T) -> R): DataCollectionImpl<R> {
        val p = paginator.map(transform)
        val s = SelectionManagerImpl(p)
        return DataCollectionImpl(p, s, actionsOf(), columnsOf())
    }
}