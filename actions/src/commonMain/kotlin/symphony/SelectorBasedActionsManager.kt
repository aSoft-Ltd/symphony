@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.JsExport

interface SelectorBasedActionsManager<out T> : GenericSelectorBasedActionsManager<T> {
    fun redefine(body: LinearSelectorBasedActionsBuilder<@UnsafeVariance T>.() -> Unit)
}