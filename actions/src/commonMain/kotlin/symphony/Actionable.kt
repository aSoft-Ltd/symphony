@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport


interface Actionable<T> {
    val actions: ActionsManager<T>
}