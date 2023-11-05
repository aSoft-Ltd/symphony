@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kotlin.js.JsExport
import symphony.internal.SelectionManagerImpl

class LazyList<T> @PublishedApi internal constructor(
    override val paginator: PaginationManager<T>,
    override val selector: SelectionManager<T>,
    override val actions: SelectorBasedActionsManager<T>
) : Pageable<T>, Selectable<T>, ActionableSelection<T> {
    val rows: List<Row<T>> get() = paginator.continuous

    fun <R> map(transform: (T) -> R): LazyList<R> {
        val p = paginator.map(transform)
        val s = SelectionManagerImpl(p)
        return LazyList(p, s, actionsOf())
    }
}