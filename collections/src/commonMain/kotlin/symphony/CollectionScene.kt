@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.BaseScene
import cinematic.MutableLive
import cinematic.mutableLiveOf
import keep.Cacheable
import keep.loadOrNull
import keep.save
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

    protected fun columnsOf(builder: ColumnsBuilder<T>.() -> Unit) = columnsOf<T>(cache, builder)

    open val actions: SelectorBasedActionsManager<T> by lazy { emptyActions() }

    open val columns by lazy { columnsOf<T>() }

    val list by lazy { lazyListOf(paginator) }

    val table by lazy { tableOf(paginator, selector, actions, columns) }

    private val preferredView = "${this::class.simpleName?.replace("Scene", "")}.$PREFERRED_VIEW"

    suspend fun switchToLatestSelectedView() {
        val v = cache.loadOrNull<View>(preferredView) ?: DEFAULT_VIEW
        view.value = v
    }

    suspend fun switchToListView() = switchTo(View.List)

    suspend fun switchToTableView() = switchTo(View.Table)

    private suspend fun switchTo(v: View) {
        cache.save(preferredView, v)
        view.value = v
    }

    private var searchText: String? = null
    val searchBox = TextField(name = ::searchText)

    suspend fun search(): Page {
        paginator.clearPages()
        return paginator.loadFirstPage()
    }

    suspend fun unselect(item: T? = null) {
        cache.remove(CacheKeys.SELECTED_ITEM)
        selector.unSelect(item ?: return)
    }

    fun select(item: T): T {
        selector.select(item)
        return item
    }

    private companion object {
        const val PREFERRED_VIEW = "preferred.view"

        val DEFAULT_VIEW = View.List
    }
}