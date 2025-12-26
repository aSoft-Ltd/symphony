import symphony.BalanceSheetForm
import symphony.DynamicReportRow
import symphony.SectionRow
import symphony.StaticSectionRowForm

fun BalanceSheetForm.renderToString(tab: String = " ") = buildString {
    appendLine(assets.renderToString(tab, 0))
    appendLine(equity.renderToString(tab, 0))
    appendLine(liabilities.renderToString(tab, 0))
}

fun SectionRow.renderToString(tab: String, depth: Int): String = when (this) {
    is StaticSectionRowForm -> renderToString(tab, depth)
    else -> label.state.value.output ?: ""
}

fun StaticSectionRowForm.renderToString(tab: String, depth: Int = 0) = buildString {
    append(tab.repeat(depth))
    append(label.state.value.output ?: "")
    if (children.value.isEmpty()) {
        append(tab.repeat(2))
        append(total.state.value.output ?: 0.0)
    } else {
        appendLine()
        for (child in children.value.iterator()) {
            appendLine(child.renderToString(tab, depth + 1))
        }
    }
}

private fun DynamicReportRow.renderToString(tab: String = " ", depth: Int = 0): String = buildString {
    append(tab.repeat(depth))
    append(label.output ?: "")
    if (container.output != false) {
        append(tab.repeat(depth + 10))
        appendLine(total.output ?: 0.0)
        for (row in rows.value) append(row.renderToString(tab, depth + 1))
    } else {
        append(tab.repeat(depth + 1))
        appendLine(total.output ?: 0.0)
    }
}