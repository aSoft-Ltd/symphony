package symphony

import kotlin.math.max
import symphony.columns.ActionColumn
import symphony.columns.Column
import symphony.columns.DataColumn
import symphony.columns.SelectColumn

private fun <D> ITable<D>.text(row: Row<D>, col: Column<D>) = when (col) {
    is SelectColumn -> if (selector.isRowSelectedOnCurrentPage(row.number)) "[x]" else "[ ]"
    is DataColumn -> col.resolve(row)
    is ActionColumn -> actions.of(row.item).joinToString(separator = "|") { it.name }
}

private fun <D> ITable<D>.text(col: Column<D>) = when (col) {
    is DataColumn -> col.name
    is ActionColumn -> col.name
    is SelectColumn -> when {
        selector.isCurrentPageSelectedWholly() -> "[x]"
        selector.isCurrentPageSelectedPartially() -> "[-]"
        else -> "[ ]"
    }
}

private fun <D> ITable<D>.calculateColSizes(gap: Int): MutableMap<Column<D>, Int> {
    val colSizes = mutableMapOf<Column<D>, Int>()
    val visibleColumns = columns.all().filter { it.visibility.isVisible }
    rows.forEach { row ->
        visibleColumns.forEach { col ->
            colSizes[col] = maxOf(colSizes[col] ?: 0, text(row, col).length + gap)
        }
    }
    return colSizes
}

private fun StringBuilder.appendRow(text: String, size: Int?) {
    append(text + " ".repeat(max((size ?: 0) - text.length, 0)))
}

fun <D> ITable<D>.renderToString(gap: Int = 4) = buildString {
    val colSizes = calculateColSizes(gap)
    val visibleColumns = columns.all().filter { it.visibility.isVisible }
    visibleColumns.forEach { appendRow(text(it), colSizes[it]) }
    appendLine()
    appendLine()
    rows.forEach { row ->
        visibleColumns.forEach { col -> appendRow(text(row, col), colSizes[col]) }
        appendLine()
    }
}