@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kotlinx.JsExport

/**
 * A model representation of what a GroupedPage of data should contain
 */
data class GroupedPage<out G, out T>(
    val groups: List<Chunk<G, Row<T>>>,
    override val capacity: Int,
    override val number: Int
) : AbstractPage() {

    override val size by lazy { groups.flatMap { it.items }.size }
    override fun toString(): String = buildString {
        appendLine("GroupedPage(number = $number, size= $size/$capacity)")
        appendLine()
        groups.forEach { chunk ->
            appendLine(chunk.group)
            chunk.items.forEach {
                appendLine("  ${it.number}. ${it.item}")
            }
        }
        appendLine()
    }
}