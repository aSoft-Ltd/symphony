@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kotlinx.JsExport

interface SingleChoiceFieldState<out O> : BaseFieldState<O>, SearchableState {

    val items: List<O & Any>

    val selectedItem: O?

    val selectedOption: Option?
}
