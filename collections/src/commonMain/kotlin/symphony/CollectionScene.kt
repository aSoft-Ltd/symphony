@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.BaseScene
import keep.load
import keep.save
import keep.Cacheable
import koncurrent.Later
import koncurrent.later.then
import koncurrent.later.andThen
import koncurrent.later.andZip
import koncurrent.later.zip
import koncurrent.later.catch
import koncurrent.later.finally
import cinematic.MutableLive
import cinematic.mutableLiveOf
import kotlinx.JsExport

@Deprecated(
    message = "In favour of LazyCollectionScene",
    replaceWith = ReplaceWith("LazyCollectionScene", "symphony.LazyCollectionScene")
)
abstract class CollectionScene<T>(private val config: Cacheable) : BaseScene() {

    val view: MutableLive<View> = mutableLiveOf(DEFAULT_VIEW)

    val cache = config.cache

    open val paginator by lazy { linearPaginatorOf<T>() }

    open val selector by lazy { selectorOf(paginator) }

    protected fun columnsOf(builder: ColumnsBuilder<T>.() -> Unit) = columnsOf<T>(builder)

    open val actions: SelectorBasedActionsManager<T> by lazy { emptyActions() }

    open val columns by lazy { columnsOf<T>() }

    val list by lazy { lazyListOf(paginator) }

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

    private var searchText: String? = null
    val searchBox = TextField(name = ::searchText)

    fun search(): Later<Page> {
        paginator.clearPages()
        return paginator.loadFirstPage()
    }

    fun unselect(item: T? = null) {
        cache.remove(CacheKeys.SELECTED_ITEM)
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