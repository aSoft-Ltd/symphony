package symphony

import cinematic.Watcher
import cinematic.mutableLiveListOf
import kollections.find
import kollections.sumOf

internal class DynamicReportRowImpl(
    private val title: String
) : DynamicReportRow {
    override val label: BaseField<String> = TextField(name = "$title-label", label = title)
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
        add("Other")
        watcher = rows.watchEagerly { updateTotal() }
    }

    override fun add(name: String): DynamicReportRow? {
        if (!isContainer) return null
        val row = DynamicReportRowImpl(name)
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
        if (!isContainer) return null
        val candidate = rows.value.find { it == child } ?: return null
        candidate.total.state.stopAll()
        rows.remove(candidate)
        return candidate
    }
}