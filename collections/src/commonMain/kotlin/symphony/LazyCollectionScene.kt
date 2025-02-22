@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.BaseScene
import keep.Cacheable
import keep.load
import keep.save
import koncurrent.Later
import koncurrent.later.finally
import kotlinx.JsExport

abstract class LazyCollectionScene<T>(config: Cacheable) : BaseScene() {

    private val cache by lazy {
        val namespace = "${this::class.simpleName?.replace("Scene", "")}".lowercase()
        config.cache.namespaced(namespace)
    }

    val views = viewsOf(*View.values()) {
        cache.save(PREFERRED_VIEW, it)
    }

    abstract val paginator: PaginationManager<T, *, *>

    abstract val selector: SelectionManager<T, *>

//    abstract val sorter: ColumnSorter

    protected fun columnsOf(builder: ColumnsBuilder<T>.() -> Unit) = columnsOf<T>(cache,builder)

    open val actions: SelectorBasedActionsManager<T> by lazy { emptyActions() }

    open val columns by lazy { columnsOf<T>() }

    abstract val list: LazyList<T>

    val table by lazy { tableOf(paginator, selector, actions, columns) }

    fun switchToLatestSelectedView() = cache.load<View>(PREFERRED_VIEW).finally {
        views.select(it.data ?: DEFAULT_VIEW)
    }

    fun search(): Later<Any?> {
        paginator.clearPages()
        return paginator.loadFirstPage()
    }

    fun unselect(item: T? = null) {
        selector.unSelect(item ?: return)
    }

    fun select(item: T): Later<T> {
        selector.select(item)
        return Later(item)
    }

    private companion object {
        const val PREFERRED_VIEW = "preferred.view"

        val DEFAULT_VIEW = View.List
    }

    fun isGroupedList() = this is GroupedCollectionScene //temp fix though cyclic
}