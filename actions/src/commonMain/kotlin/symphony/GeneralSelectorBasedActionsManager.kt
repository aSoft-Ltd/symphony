@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.JsExport

interface GeneralSelectorBasedActionsManager<out T> : GenericSelectorBasedActionsManager<T> {
    fun redefine(body: GeneralSelectorBasedActionsBuilder<@UnsafeVariance T>.() -> Unit)
}