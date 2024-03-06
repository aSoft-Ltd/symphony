@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.BaseScene
import cinematic.MutableLive
import cinematic.mutableLiveOf
import keep.Cacheable
import keep.load
import keep.save
import koncurrent.Later
import koncurrent.later.finally
import kotlinx.JsExport

abstract class LazyCollectionScene<T>(config: Cacheable) : BaseScene() {

    val view: MutableLive<View> = mutableLiveOf(DEFAULT_VIEW)

    private val cache = config.cache

    abstract val paginator: PaginationManager<T, *, *>

    abstract val selector: SelectionManager<T, *>

    protected fun columnsOf(builder: ColumnsBuilder<T>.() -> Unit) = columnsOf<T>(builder)

    open val actions: SelectorBasedActionsManager<T> by lazy { emptyActions() }

    open val columns by lazy { columnsOf<T>() }

    abstract val list: LazyList<T>

    val table by lazy { tableOf(paginator, selector, actions, columns) }

    private val preferredView = "${this::class.simpleName?.replace("Scene", "")}.$PREFERRED_VIEW"

    fun switchToLatestSelectedView() = cache.load<View>(preferredView).finally {
        view.value = it.data ?: DEFAULT_VIEW
    }

    fun switchToListView() = switchTo(View.List)

    fun switchToTableView() = switchTo(View.Table)

    private fun switchTo(v: View) = cache.save(preferredView, v).finally {
        view.value = v
    }

    fun search(): Later<Page> {
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
}