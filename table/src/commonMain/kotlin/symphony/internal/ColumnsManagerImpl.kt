package symphony.internal

import cinematic.mutableLiveSetOf
import kollections.toISet
import symphony.ColumnMover
import symphony.ColumnsBuilder
import symphony.ColumnsManager
import symphony.Row
import symphony.Visibilities
import symphony.Visibility
import symphony.columns.Column
import symphony.columns.DataColumn

@PublishedApi
internal class ColumnsManagerImpl<D>(initializer: ColumnsBuilder<D>.() -> Unit) : ColumnsManager<D> {
    override val current by lazy {
        val cmb = ColumnsBuilder<D>().apply(initializer)
        mutableLiveSetOf<Column<D>>(cmb.columns.values.sortedBy { it.index })
    }

    override fun all() = current.value

    override fun find(name: String) = all().find { it.name.contentEquals(name, ignoreCase = true) }

    override fun hide(name: String): ColumnsManager<D> {
        val column = find(name)?.copy(visibility = Visibilities.Hidden) ?: return this
        replace(name, column)
        return this
    }

    override fun show(name: String): ColumnsManager<D> {
        val column = find(name)?.copy(visibility = Visibilities.Visible) ?: return this
        replace(name, column)
        return this
    }

    override fun toggleVisibility(name: String): ColumnsManager<D> {
        val column = find(name) ?: return this
        return if (column.visibility.isVisible) {
            hide(name)
        } else {
            show(name)
        }
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
        val old = find(name) ?: return this
        val new = old.copy(index = idx)
        val columns = all().toMutableList()
        columns.remove(old)
        if (idx < columns.size) {
            columns.add(idx, new)
        } else {
            columns.add(new)
        }
        current.value = columns.mapIndexed { index, column ->
            column.copy(index = index)
        }.toISet()
        return this
    }

    override fun move(name: String) = ColumnsMoverImpl(name)

    inner class ColumnsMoverImpl(private val column: String) : ColumnMover<D> {
        override fun before(name: String): ColumnsManager<D> {
            val anchor = find(name) ?: return this@ColumnsManagerImpl
            return index(column, anchor.index)
        }

        override fun after(name: String): ColumnsManager<D> {
            val anchor = find(name) ?: return this@ColumnsManagerImpl
            return index(column, anchor.index + 1)
        }
    }

    override fun add(name: String, accessor: (Row<D>) -> String): ColumnsManager<D> {
        find(name)?.let { current.remove(it) }
        current.add(DataColumn(name, name, current.size, Visibilities.Visible, name, accessor))
        return this
    }

    private fun replace(name: String, column: Column<D>) {
        val c = find(name) ?: return
        val old = all()
        current.value = ((old - c) + column).sortedBy { it.index }.toISet()
    }
}