@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport

interface FixedActionsManager : ActionsManager<Any> {
    fun refresh()
}