@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport

interface LazyList<out T> {
    val paginator: IPaginationManager<T, *, *>
    val selector: ISelectionManager<T, *>
    val actions: SelectorBasedActionsManager<T>
}