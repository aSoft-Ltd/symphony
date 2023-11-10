package symphony.internal

import kollections.List
import kollections.toIList
import symphony.GroupedPaginationManager
import symphony.LinearList
import symphony.Row

@PublishedApi
internal class GroupedPaginatedLinearList<T> @PublishedApi internal constructor(
    override val paginator: GroupedPaginationManager<*, T>
) : LinearList<T> {
    override val rows: List<Row<T>> get() = paginator.continuous.flatMap { it.items }.toIList()
}