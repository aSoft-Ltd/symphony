package symphony.internal

import symphony.Chunk
import symphony.GroupedList
import symphony.Grouper
import symphony.LinearPaginationManager
import symphony.Row

@PublishedApi
internal class LinearlyPaginatedGroupedList<G, T> @PublishedApi internal constructor(
    override val paginator: LinearPaginationManager<T>,
    private val grouper: Grouper<G, T>
) : GroupedList<G, T> {

    override val groups: List<Chunk<G, Row<T>>> get() = paginator.continuous.map { it.item }.let { grouper.group(it) }

    override fun toString(): String = buildString {
        groups.forEach { chunk ->
            appendLine(chunk.group)
            chunk.items.forEach {
                appendLine("\t${it.item}")
            }
        }
    }
}