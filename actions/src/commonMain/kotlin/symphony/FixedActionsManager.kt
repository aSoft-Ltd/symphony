@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kevlar.builders.Actions0Builder
import kotlinx.JsExport

interface FixedActionsManager : ActionsManager<Any> {
    fun redefine(body: Actions0Builder<Unit>.() -> Unit)
    fun refresh()
}