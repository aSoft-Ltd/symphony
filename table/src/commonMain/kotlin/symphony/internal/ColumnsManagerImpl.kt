package symphony.internal

import cinematic.mutableLiveSetOf
import keep.Cache
import kollections.Set
import kollections.SetSerializer
import kollections.add
import kollections.emptySet
import kollections.find
import kollections.map
import kollections.mapIndexed
import kollections.minus
import kollections.plus
import kollections.remove
import kollections.size
import kollections.sortedBy
import kollections.toMutableList
import kollections.toSet
import kollections.values
import koncurrent.Later
import koncurrent.awaited.catch
import koncurrent.awaited.then
import koncurrent.toLater
import kotlinx.serialization.Serializable
import symphony.ColumnAppender
import symphony.ColumnsBuilder
import symphony.ColumnsManager
import symphony.HiddenVisibility
import symphony.Mover
import symphony.Row
import symphony.Visibilities
import symphony.Visibility
import symphony.VisibleVisibility
import symphony.columns.ActionColumn
import symphony.columns.Column
import symphony.columns.DataColumn
import symphony.columns.SelectColumn

@PublishedApi
internal class ColumnsManagerImpl<D>(
    private val cache: Cache?,
    initializer: ColumnsBuilder<D>.() -> Unit
) : ColumnsManager<D> {

    override val current by lazy {
        val cmb = ColumnsBuilder<D>().apply(initializer)
        mutableLiveSetOf<Column<D>>(cmb.columns.values.sortedBy { it.index })
    }

    @Serializable
    data class ColumnCache(
        val name: String,
        val hidden: Boolean,
        val index: Int,
    )

    companion object {
        private val serializer by lazy { SetSerializer(ColumnCache.serializer()) }
        private const val CACHE_KEY = "columns"
    }

    override fun initialize() = cachedColumns().then { columns ->
        current.value = current.value.map { column ->
            val cached = columns?.find { it.name == column.name } ?: return@map column
            column.copy(
                visibility = if (cached.hidden) Visibilities.Hidden else Visibilities.Visible,
                index = cached.index
            )
        }.sortedBy { it.index }.toSet()
    }.then {
        this
    }

    override val append by lazy { ColumnsAppenderImpl() }

    override fun reset(): ColumnsManager<D> {
        return this
    }

    override fun all() = current.value

    override fun find(name: String) = all().find { it.name.contentEquals(name, ignoreCase = true) }

    override fun hide(name: String): Later<ColumnsManager<D>> {
        val column = find(name)?.copy(visibility = Visibilities.Hidden) ?: return toLater()
        return replace(name, column)
    }

    override fun show(name: String): Later<ColumnsManager<D>> {
        val column = find(name)?.copy(visibility = Visibilities.Visible) ?: return toLater()
        return replace(name, column)
    }

    override fun toggleVisibility(name: String): Later<ColumnsManager<D>> {
        val column = find(name) ?: return toLater()
        return if (column.visibility.isVisible) {
            hide(name)
        } else {
            show(name)
        }
    }

    override fun rename(prev: String, curr: String): Later<ColumnsManager<D>> {
        val column = find(prev)?.copy(name = curr) ?: return toLater()
        return replace(prev, column)
    }

    override fun remove(name: String): Later<ColumnsManager<D>> {
        val column = find(name) ?: return toLater()
        current.value = (all() - column).toSet()
        return cacheColumns()
    }

    override fun move(name: String) = ColumnsMoverImpl(name)

    inner class ColumnsAppenderImpl : ColumnAppender<D> {
        override fun selectable(name: String) = append(name) { index, visibility ->
            SelectColumn(name, name, index, visibility)
        }

        override fun data(name: String, accessor: (Row<D>) -> Any?) = append(name) { index, visibility ->
            DataColumn(name, name, index, visibility, name, accessor)
        }

        override fun actions(name: String) = append(name) { index, visibility ->
            ActionColumn(name, name, index, visibility)
        }

        private fun append(name: String, block: (index: Int, visibility: Visibility) -> Column<D>): Later<ColumnAppender<D>> {
            val existing = find(name)
            if (existing != null) return toLater()
            return cachedColumns().then { columns ->
                val cached = columns?.find { it.name == name }
                val visibility = if (cached?.hidden != true) VisibleVisibility else HiddenVisibility
                current.add(block(cached?.index ?: current.value.size, visibility))
            }.then {
                this
            }
        }
    }

    inner class ColumnsMoverImpl(private val column: String) : Mover {

        override fun at(index: Int): Later<Any> {
            val old = find(column) ?: return toLater()
            val new = old.copy(index = index)
            val columns = all().toMutableList()
            columns.remove(old)
            if (index < columns.size) {
                columns.add(index, new)
            } else {
                columns.add(new)
            }
            current.value = columns.sortedBy { it.index }.mapIndexed { idx, column ->
                column.copy(index = idx)
            }.toSet()
            return cacheColumns()
        }

        override fun before(name: String): Later<Any> {
            val anchor = find(name) ?: return toLater()
            val subject = find(column) ?: return toLater()
            if (subject.index <= anchor.index) return toLater()
            return at(anchor.index)
        }

        override fun after(name: String): Later<Any> {
            val anchor = find(name) ?: return toLater()
            val subject = find(column) ?: return toLater()
            if (subject.index >= anchor.index + 1) return toLater()
            return at(anchor.index + 1)
        }
    }

    override fun add(name: String, accessor: (Row<D>) -> Any?): Later<ColumnsManager<D>> {
        val existing = find(name)
        if (existing != null) return toLater()
        return cachedColumns().then { columns ->
            val cached = columns?.find { it.name == name }
            val visibility = if (cached?.hidden != true) VisibleVisibility else HiddenVisibility
            current.add(DataColumn(name, name, cached?.index ?: current.value.size, visibility, name, accessor))
        }.then {
            this
        }
    }

    private fun cacheColumns(): Later<ColumnsManagerImpl<D>> {
        return cache?.save(CACHE_KEY, all().map { it.toCached() }, serializer)?.then { this } ?: toLater()
    }

    private fun cachedColumns(): Later<Set<ColumnCache>?> {
        return cache?.load(CACHE_KEY, serializer)?.catch { emptySet() } ?: emptySet<ColumnCache>().toLater()
    }

    private fun replace(name: String, column: Column<D>): Later<ColumnsManager<D>> {
        val c = find(name) ?: return toLater()
        val old = all()
        current.value = ((old - c) + column).sortedBy { it.index }.toSet()
        return cacheColumns()
    }

    private fun Column<D>.toCached() = ColumnCache(name, visibility.isHidden, index)
}