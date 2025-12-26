package symphony

fun SpreadSheet.rendererToString(tab: Int = 4) = buildString {
    val t = " ".repeat(tab)
    append("Name$t")
    state.value.columns.forEach {
        append("$it$t")
    }
    appendLine()
    state.value.structure.flatMap { it.flatten() }.forEach {
        append(it.renderToString())
        appendLine(state.value.cells.renderToString(it.field, tab))
    }
}

private class IndentedRowField(
    val indention: Int,
    val field: RowTemplate
)

private fun RowTemplate.flatten(indent: Int = 0): List<IndentedRowField> {
    val list = mutableListOf<IndentedRowField>()
    list.add(IndentedRowField(indent, this))
    asGrouped?.children?.forEach {
        list.addAll(it.flatten(indent + 1))
    }
    return list
}

private fun IndentedRowField.renderToString() = buildString {
    val space = " ".repeat(indention)
    append(space)
    when (field) {
        is CapturedRowTemplate -> {
            append(field.label)
        }

        is ComputedRowTemplate -> {
            append(field.label)
        }

        is GroupedRowTemplate -> {
            appendLine(field.label)
        }
    }
}

internal fun Map<String, Map<String, SheetCell>>.renderToString(row: RowTemplate, tab: Int) = buildString {
    val t = " ".repeat(tab)
    entries.filter { it.key == row.label && row !is GroupedRowTemplate }.forEach { entry ->
        append(t)
        entry.value.values.forEach { col ->
            append("${col.value}$t")
        }
        appendLine()
    }
}