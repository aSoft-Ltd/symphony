@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package symphony

import kollections.Collection
import kotlin.js.JsExport

sealed interface ChoiceField<O> {
    val items: Collection<O>
    val mapper: (O) -> Option
}