@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.JsExport

interface MultiChoiceFieldState<out O> : ChoiceFieldState<O>, BaseFieldState<List<O>> {
    val selected: MultiSelectedChoice<O>
}
