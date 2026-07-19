package symphony.internal

import cinematic.mutableLiveOf
import cinematic.mutableLiveSetOf
import keep.Cache
import keep.loadOrNull
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.SetSerializer
import symphony.ColumnAppender
import symphony.ColumnsBuilder
import symphony.ColumnsManager
import symphony.HiddenVisibility
import symphony.Mover
import symphony.Row
import symphony.Tweaks
import symphony.Visibilities
import symphony.Visibility
import symphony.VisibleVisibility
import symphony.columns.ActionColumn
import symphony.columns.Column
import symphony.columns.DataColumn
import symphony.columns.Filter
import symphony.columns.Order
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

    override val tweaks by lazy { mutableLiveOf(Tweaks(null, mapOf())) }

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

    override suspend fun initialize(): ColumnsManager<D> {
        val columns = cachedColumns()
        current.value = current.value.map { column ->
            val cached = columns.find { it.name == column.name } ?: return@map column
            column.copy(
                visibility = if (cached.hidden) Visibilities.Hidden else Visibilities.Visible,
                index = cached.index
            )
        }.sortedBy { it.index }.toSet()
        return this
    }

    override val append by lazy { ColumnsAppenderImpl() }

    override fun reset(): ColumnsManager<D> {
        return this
    }

    override fun all() = current.value

    override fun find(name: String) = all().find { it.name.contentEquals(name, ignoreCase = true) }

    override suspend fun hide(name: String): ColumnsManager<D> {
        val column = find(name)?.copy(visibility = Visibilities.Hidden) ?: return this
        return replace(name, column)
    }

    override suspend fun show(name: String): ColumnsManager<D> {
        val column = find(name)?.copy(visibility = Visibilities.Visible) ?: return this
        return replace(name, column)
    }

    override suspend fun toggleVisibility(name: String): ColumnsManager<D> {
        val column = find(name) ?: return this
        return if (column.visibility.isVisible) {
            hide(name)
        } else {
            show(name)
        }
    }

    override suspend fun rename(prev: String, curr: String): ColumnsManager<D> {
        val column = find(prev)?.copy(name = curr) ?: return this
        return replace(prev, column)
    }

    override suspend fun remove(name: String): ColumnsManager<D> {
        val column = find(name) ?: return this
        current.value = (all() - column).toSet()
        return cacheColumns()
    }

    override fun move(name: String) = ColumnsMoverImpl(name)

    inner class ColumnsAppenderImpl : ColumnAppender<D> {
        override suspend fun selectable(name: String) = append(name) { index, visibility ->
            SelectColumn(name, name, index, visibility)
        }

        override suspend fun data(name: String, accessor: (Row<D>) -> Any?) = append(name) { index, visibility ->
            DataColumn(name, name, index, visibility, name, accessor)
        }

        override suspend fun actions(name: String) = append(name) { index, visibility ->
            ActionColumn(name, name, index, visibility)
        }

        private suspend fun append(name: String, block: (index: Int, visibility: Visibility) -> Column<D>): ColumnAppender<D> {
            val existing = find(name)
            if (existing != null) return this
            val columns = cachedColumns()
            val cached = columns.find { it.name == name }
            val visibility = if (cached?.hidden != true) VisibleVisibility else HiddenVisibility
            current.add(block(cached?.index ?: current.value.size, visibility))
            return this
        }
    }

    inner class ColumnsMoverImpl(private val column: String) : Mover {

        override suspend fun at(index: Int) {
            val old = find(column) ?: return
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
            cacheColumns()
        }

        override suspend fun before(name: String) {
            val anchor = find(name) ?: return
            val subject = find(column) ?: return
            if (subject.index <= anchor.index) return
            return at(anchor.index)
        }

        override suspend fun after(name: String) {
            val anchor = find(name) ?: return
            val subject = find(column) ?: return
            if (subject.index >= anchor.index + 1) return
            return at(anchor.index + 1)
        }
    }

    @Deprecated("in favor of append.data", replaceWith = ReplaceWith("append.data(name, accessor)"))
    override suspend fun add(name: String, accessor: (Row<D>) -> Any?): ColumnsManager<D> {
        val existing = find(name)
        if (existing != null) return this
        val columns = cachedColumns()
        val cached = columns.find { it.name == name }
        val visibility = if (cached?.hidden != true) VisibleVisibility else HiddenVisibility
        current.add(DataColumn(name, name, cached?.index ?: current.value.size, visibility, name, accessor))
        return this
    }

    private suspend fun cacheColumns(): ColumnsManagerImpl<D> {
        cache?.save(CACHE_KEY, all().map { it.toCached() }.toSet(), serializer)
        return this
    }

    private suspend fun cachedColumns(): Set<ColumnCache> = cache?.loadOrNull(CACHE_KEY, serializer) ?: emptySet()

    private suspend fun replace(name: String, column: Column<D>): ColumnsManager<D> {
        val c = find(name) ?: return this
        val old = all()
        current.value = ((old - c) + column).sortedBy { it.index }.toSet()
        return cacheColumns()
    }

    private fun Column<D>.toCached() = ColumnCache(name, visibility.isHidden, index)

    override fun redefine(block: ColumnsBuilder<D>.() -> Unit) {
        val cmb = ColumnsBuilder<D>().apply(block)
        current.value = cmb.columns.values.sortedBy { it.index }.toSet()
    }

    override fun sort(name: String): Order = if (tweaks.value.sort?.first == name) tweaks.value.sort?.second ?: Order.None else Order.None

    override fun sort(name: String, order: Order) {
        tweaks.value = tweaks.value.copy(sort = if (order == Order.None) null else name to order)
    }

    override fun filter(name: String, filter: Filter) {
        val filters = buildMap {
            putAll(tweaks.value.filters)
            if (filter is Filter.None) remove(name) else put(name, filter)
        }
        tweaks.value = tweaks.value.copy(filters = filters)

    }
}