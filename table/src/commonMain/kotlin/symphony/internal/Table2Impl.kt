package symphony.internal

import symphony.ColumnsManager
import symphony.Paginator
import symphony.Row
import symphony.Selector
import symphony.SelectorBasedActionsManager
import symphony.table.Table

@PublishedApi
internal class Table2Impl<T>(
    override val paginator: Paginator<T>,
    override val selector: Selector<T>,
    override val actions: SelectorBasedActionsManager<T>,
    override val columns: ColumnsManager<T>
) : Table<T> {
    override val rows: List<Row<T>> get() = paginator.state.value.items.content.mapIndexed { idx, it -> Row(idx, it) }
}