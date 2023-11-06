package symphony.internal

import kollections.List
import kollections.iEmptyList
import kollections.toIList
import symphony.ColumnsManager
import symphony.GroupedPaginationManager
import symphony.GroupedSelectionManager
import symphony.GroupedTable
import symphony.LinearPaginationManager
import symphony.LinearSelectionManager
import symphony.LinearTable
import symphony.Row
import symphony.SelectorBasedActionsManager

@PublishedApi
internal class GroupedTableImpl<T>(
    override val paginator: GroupedPaginationManager<*, T>,
    override val selector: GroupedSelectionManager<*, T>,
    override val actions: SelectorBasedActionsManager<T>,
    override val columns: ColumnsManager<T>
) : GroupedTable<T> {
    override val rows: List<Row<T>> get() = paginator.currentPageOrNull?.groups?.flatMap { it.items }?.toIList() ?: iEmptyList()
}