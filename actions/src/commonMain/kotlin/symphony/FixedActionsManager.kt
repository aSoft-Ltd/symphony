@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.JsExport

interface FixedActionsManager : ActionsManager<Any> {
    fun refresh()
}