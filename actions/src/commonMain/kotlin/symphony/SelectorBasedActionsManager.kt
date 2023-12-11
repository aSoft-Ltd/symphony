@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kevlar.Action0
import kollections.List
import kotlinx.JsExport

interface SelectorBasedActionsManager<out T> : ActionsManager<T> {

    fun addSingle(name: String, handler: (T) -> Unit): SelectorBasedActionsManager<T>

    fun addMulti(name: String, handler: (List<T>) -> Unit): SelectorBasedActionsManager<T>

    fun of(item: @UnsafeVariance T): List<Action0<Unit>>
}