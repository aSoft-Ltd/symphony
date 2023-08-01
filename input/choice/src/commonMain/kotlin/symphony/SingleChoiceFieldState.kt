@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kotlin.js.JsExport

interface SingleChoiceFieldState<out O> : BaseFieldState<O> {
    val items: List<O & Any>

    val selectedItem: O?

    val selectedOption: Option?
}
