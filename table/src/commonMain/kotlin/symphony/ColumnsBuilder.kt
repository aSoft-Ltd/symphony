package symphony

import kollections.mutableMapOf
import kollections.set
import kollections.size
import symphony.columns.ActionColumn
import symphony.columns.Column
import symphony.columns.DataColumn
import symphony.columns.SelectColumn

class ColumnsBuilder<D> @PublishedApi internal constructor() {

    internal val columns = mutableMapOf<String, Column<D>>()

    fun selectable(name: String = "Select", key: String = name) {
        columns[name] = SelectColumn(name, key, columns.size, Visibilities.Visible)
    }

    fun column(name: String, key: String = name, default: String = "N/A", accessor: (Row<D>) -> Any?) {
        columns[name] = DataColumn(name, key, columns.size, Visibilities.Visible, default, accessor)
    }

    fun action(name: String, key: String) {
        columns[name] = ActionColumn(name, key, columns.size, Visibilities.Visible)
    }
}