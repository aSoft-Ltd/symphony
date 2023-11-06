package symphony.internal

import kollections.List
import symphony.ActionableSelection
import symphony.Chunk
import symphony.GroupedList
import symphony.Grouper
import symphony.LinearPaginationManager
import symphony.LinearSelectionManager
import symphony.Row
import symphony.SelectorBasedActionsManager

@PublishedApi
internal class LocallyGroupedList<G, T> @PublishedApi internal constructor(
    override val paginator: LinearPaginationManager<T>,
    private val grouper: Grouper<G, T>,
    override val selector: LinearSelectionManager<T>,
    override val actions: SelectorBasedActionsManager<T>
) : GroupedList<G, T>, ActionableSelection<T> {

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