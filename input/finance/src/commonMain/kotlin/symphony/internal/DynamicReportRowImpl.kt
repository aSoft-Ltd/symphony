package symphony.internal

import cinematic.Watcher
import cinematic.mutableLiveListOf
import kollections.find
import kollections.isEmpty
import kollections.sumOf
import symphony.BaseField
import symphony.BooleanField
import symphony.DoubleField
import symphony.DynamicReportRow
import symphony.TextField

internal class DynamicReportRowImpl(
    title: String,
    override val removable: Boolean,
    override val appendable: Boolean
) : DynamicReportRow {
    override val label: BaseField<String> = TextField(name = "$title-label", label = title, value = title)
    override val container: BooleanField = BooleanField(name = "$title-container", value = false)
    override val total = DoubleField(name = "$title-total", label = "Total", value = 0.0)
    override val rows = mutableLiveListOf<DynamicReportRow>()

    private val isContainer get() = container.output == true

    private var watcher: Watcher? = null

    override fun collapse() {
        if (container.output == false) return
        val sum = rows.value.sumOf { it.total.output ?: 0.0 }
        total.set(value = sum)
        container.set(false)
        watcher?.stop()
        watcher = null
        rows.clear()
    }

    override fun expand() {
        if (container.output == true) return
        container.set(true)
        watcher = rows.watchEagerly { updateTotal() }
    }

    override fun add(name: String) = add(name, true, true)

    internal fun add(name: String, removable: Boolean, appendable: Boolean): DynamicReportRow? {
        if (!isContainer) return null
        val row = DynamicReportRowImpl(name, removable, appendable)
        row.total.state.watchEagerly { updateTotal() }
        rows.add(row)
        return row
    }

    private fun updateTotal() {
        if (!isContainer) return
        val sum = rows.value.sumOf { it.total.output ?: 0.0 }
        this.total.set(value = sum)
    }

    override fun remove(child: DynamicReportRow?): DynamicReportRow? {
        if (!isContainer || !removable) return null
        val candidate = rows.value.find { it == child } ?: return null
        candidate.total.state.stopAll()
        rows.remove(candidate)
        if (rows.value.isEmpty()) container.set(false)
        updateTotal()
        return candidate
    }
}