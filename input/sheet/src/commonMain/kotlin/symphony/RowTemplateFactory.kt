@file:JsExport

package symphony

import kollections.List
import kollections.add
import kollections.mutableListOf
import kotlinx.JsExport

class RowFieldBuilder {
    internal val children = mutableListOf<RowTemplate>()

    fun group(label: String, bold: Boolean = true, builder: RowFieldBuilder.() -> Unit) {
        children.add(buildGroupedRowField(label, bold, builder))
    }

    fun captured(label: String, bold: Boolean = false) {
        children.add(buildCapturedRowField(label, bold))
    }

    fun computed(label: String, bold: Boolean = true, updater: (CellGetter) -> Double) {
        children.add(buildComputedField(label, bold, updater))
    }
}

internal fun buildGroupedRowField(
    label: String,
    bold: Boolean = false,
    builder: RowFieldBuilder.() -> Unit
): GroupedRowTemplate {
    val rfb = RowFieldBuilder()
    rfb.apply(builder)
    return GroupedRowTemplate(label, bold, rfb.children)
}

internal fun buildCapturedRowField(
    label: String,
    bold: Boolean = false,
) = CapturedRowTemplate(label, bold)

internal fun buildComputedField(
    label: String,
    bold: Boolean = false,
    updater: (CellGetter) -> Double
) = ComputedRowTemplate(label, bold, updater)

fun buildTemplate(builder: RowFieldBuilder.() -> Unit): List<RowTemplate> {
    val tb = RowFieldBuilder()
    tb.apply(builder)
    return tb.children
}