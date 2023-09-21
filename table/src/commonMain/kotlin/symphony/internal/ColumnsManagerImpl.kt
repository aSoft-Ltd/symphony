package symphony.internal

import cinematic.mutableLiveSetOf
import kollections.toISet
import symphony.Column
import symphony.ColumnsManager
import symphony.ColumnsBuilder
import symphony.Row
import symphony.Visibility

@PublishedApi
internal class ColumnsManagerImpl<D>(initializer: ColumnsBuilder<D>.() -> Unit) : ColumnsManager<D> {
    override val current by lazy {
        val cmb = ColumnsBuilder<D>().apply(initializer)
        mutableLiveSetOf<Column<D>>(cmb.columns.values)
    }

    override fun all() = current.value

    override fun find(name: String) = all().find { it.name == name }

    override fun hide(name: String): ColumnsManager<D> {
        val column = find(name)?.copy(visibility = Visibility.Hidden) ?: return this
        replace(name, column)
        return this
    }

    override fun show(name: String): ColumnsManager<D> {
        val column = find(name)?.copy(visibility = Visibility.Visible) ?: return this
        replace(name, column)
        return this
    }

    override fun rename(prev: String, curr: String): ColumnsManager<D> {
        val column = find(prev)?.copy(name = curr) ?: return this
        replace(prev, column)
        return this
    }


    override fun remove(name: String): ColumnsManager<D> {
        val column = find(name) ?: return this
        current.value = (all() - column).toISet()
        return this
    }

    override fun index(name: String, idx: Int): ColumnsManager<D> {
        val column = find(name)?.copy(index = idx) ?: return this
        replace(name, column)
        return this
    }

    override fun add(name: String, accessor: (Row<D>) -> String): ColumnsManager<D> {
        current.add(Column.Data(name, name, current.size, Visibility.Visible, name, accessor))
        return this
    }

    private fun replace(name: String, column: Column<D>) {
        val c = find(name) ?: return
        val old = all()
        current.value = ((old - c) + column).toISet()
    }
}