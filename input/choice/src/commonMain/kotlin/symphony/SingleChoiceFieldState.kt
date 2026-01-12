@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.JsExport

interface SingleChoiceFieldState<out O> : ChoiceFieldState<O>, BaseFieldState<O> {
    val selected: SingleSelectedChoice<O>?
}
