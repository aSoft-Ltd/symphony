package symphony.internal

import kollections.List
import kollections.iEmptyList
import symphony.ColumnsManager
import symphony.LinearPaginationManager
import symphony.LinearSelectionManager
import symphony.LinearTable
import symphony.Row
import symphony.SelectorBasedActionsManager

@PublishedApi
internal class LinearTableImpl<T>(
    override val paginator: LinearPaginationManager<T>,
    override val selector: LinearSelectionManager<T>,
    override val actions: SelectorBasedActionsManager<T>,
    override val columns: ColumnsManager<T>
) : LinearTable<T> {
    override val rows: List<Row<T>> get() = paginator.currentPageOrNull?.items ?: iEmptyList()
}