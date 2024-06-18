import kollections.isEmpty
import kollections.iterator
import symphony.BalanceSheetForm
import symphony.BalanceSheetTopBag
import symphony.SectionRow
import symphony.StaticSectionRowForm

fun BalanceSheetForm.renderToString(tab: String = " ") = buildString {
    appendLine(assets.renderToString(tab))
    appendLine(equity.renderToString(tab))
    appendLine(liabilities.renderToString(tab))
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

fun BalanceSheetTopBag.renderToString(tab: String = " ") = buildString {
    append(tab.repeat(0))
    append(label)
    appendLine()
    for (child in listOf(current, fixed)) {
        appendLine(child.renderToString(tab, 1))
    }
}