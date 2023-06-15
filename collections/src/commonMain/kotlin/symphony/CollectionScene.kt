@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.BaseScene
import keep.load
import keep.save
import keep.Cacheable
import koncurrent.Later
import koncurrent.later.finally
import kotlinx.serialization.KSerializer
import cinematic.MutableLive
import cinematic.mutableLiveOf
import kotlin.js.JsExport

abstract class CollectionScene<T>(private val config: Cacheable) : BaseScene() {

    val view: MutableLive<View> = mutableLiveOf(DEFAULT_VIEW)

    val cache = config.cache

    @Deprecated(message = "Might not be needed")
    open val serializer: KSerializer<T>? = null

    protected inline fun columnsOf(noinline builder: ColumnsBuilder<T>.() -> Unit) = columnsOf(emptyList(), builder)

    val paginator: PaginationManager<T> by lazy { PaginationManager() }

    val selector: SelectionManager<T> by lazy { SelectionManager(paginator) }

    open val actions: SelectorBasedActionsManager<T> by lazy { actionsOf() }

    open val columns: ColumnsManager<T> by lazy { columnsOf() }

    val list: List<T> by lazy { listOf(paginator, selector, actions) }

    val table: Table<T> by lazy { tableOf(paginator, selector, actions, columns) }

    private val preferredView = "${this::class.simpleName?.replace("ViewModel", "")}.$PREFERRED_VIEW"

    fun switchToLatestSelectedView() = cache.load<View>(preferredView).finally {
        view.value = it.data ?: DEFAULT_VIEW
    }

    fun switchToListView() = switchTo(View.List)

    fun switchToTableView() = switchTo(View.Table)

    private fun switchTo(v: View) = cache.save(preferredView, v).finally {
        view.value = v
    }

    val searchBox = TextInputField(name = "search-box")

    fun search(): Later<Page<T>> {
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
//        return cache.save(CacheKeys.SELECTED_ITEM, item, serializer).catch {
//            item
//        }
    }

    private companion object {
        const val PREFERRED_VIEW = "preferred.view"

        val DEFAULT_VIEW = View.List
    }
}