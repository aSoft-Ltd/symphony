@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kevlar.Action0
import kotlinx.JsExport

interface GenericSelectorBasedActionsManager<out T> : ActionsManager<T> {

    fun addSingle(name: String, handler: (T) -> Unit): GenericSelectorBasedActionsManager<T>

    fun addMulti(name: String, handler: (List<T>) -> Unit): GenericSelectorBasedActionsManager<T>

    fun of(item: @UnsafeVariance T): List<Action0<Unit>>
}