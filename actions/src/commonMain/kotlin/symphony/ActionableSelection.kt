@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport


interface ActionableSelection<T> {
    val actions: SelectorBasedActionsManager<T>
}