package symphony.internal

import symphony.Chunk
import symphony.GroupedList
import symphony.GroupedPaginationManager
import symphony.Row

@PublishedApi
internal class GroupedPaginatedGroupedList<G, T> @PublishedApi internal constructor(
    override val paginator: GroupedPaginationManager<G, T>
) : GroupedList<G, T> {
    override val groups: List<Chunk<G, Row<T>>> get() = paginator.continuous
}