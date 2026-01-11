@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.JsExport

interface ChoiceFieldState<out O> : SearchableState {
    val items: Collection<O & Any>
}
