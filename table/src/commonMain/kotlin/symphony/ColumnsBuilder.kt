package symphony

class ColumnsBuilder<D> @PublishedApi internal constructor() {

    internal val columns = mutableMapOf<String, Column<D>>()

    fun selectable(name: String = "Select", key: String = name) {
        columns[name] = Column.Select(name, key, columns.size, Visibility.Visible)
    }

    fun column(name: String, key: String = name, default: String = "N/A", accessor: (Row<D>) -> Any?) {
        columns[name] = Column.Data(name, key, columns.size, Visibility.Visible, default, accessor)
    }

    fun action(name: String, key: String) {
        columns[name] = Column.Action(name, key, columns.size, Visibility.Visible)
    }
}