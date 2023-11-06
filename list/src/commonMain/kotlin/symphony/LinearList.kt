@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kotlin.js.JsExport

class LinearList<T> @PublishedApi internal constructor(
    override val paginator: LinearPaginationManager<T>,
    override val selector: LinearSelectionManager<T>,
    override val actions: SelectorBasedActionsManager<T>
) : LazyList<T> {
    val rows: List<Row<T>> get() = paginator.continuous
}