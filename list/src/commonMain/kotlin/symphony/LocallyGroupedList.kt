@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kotlin.js.JsExport

class LocallyGroupedList<G, T> @PublishedApi internal constructor(
    override val paginator: LinearPaginationManager<T>,
    private val grouper: Grouper<G, T>,
    override val selector: SelectionManager<T>,
    override val actions: SelectorBasedActionsManager<T>
) : GroupedList<G,T>, Selectable<T>, ActionableSelection<T> {

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