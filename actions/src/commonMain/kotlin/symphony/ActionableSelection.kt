@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.JsExport


interface ActionableSelection<T> {
    val actions: SelectorBasedActionsManager<T>
}