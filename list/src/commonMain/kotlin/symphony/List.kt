@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List as KList
import kotlin.js.JsExport
import kotlin.js.JsName
import symphony.internal.SelectionManagerImpl

@JsName("ScrollableList")
@Deprecated("In favour of LinearList")
class List<T> @PublishedApi internal constructor(
    override val paginator: PaginationManager<T>,
    override val selector: SelectionManager<T>,
    override val actions: SelectorBasedActionsManager<T>
) : Pageable<T>, Selectable<T>, ActionableSelection<T> {
    val rows: KList<Row<T>> get() = paginator.continuous

    fun <R> map(transform: (T) -> R): List<R> {
        val p = paginator.map(transform)
        val s = SelectionManagerImpl(p)
        return List(p, s, actionsOf())
    }
}