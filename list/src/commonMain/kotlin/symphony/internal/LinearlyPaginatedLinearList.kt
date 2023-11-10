package symphony.internal

import kollections.List
import symphony.LinearList
import symphony.LinearPaginationManager
import symphony.Row

@PublishedApi
internal class LinearlyPaginatedLinearList<T> @PublishedApi internal constructor(
    override val paginator: LinearPaginationManager<T>
) : LinearList<T> {
    override val rows: List<Row<T>> get() = paginator.continuous
}