@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package symphony

import kollections.Collection
import kotlinx.JsExport

sealed interface ChoiceField<O> {
    val items: Collection<O & Any>
    val mapper: (O & Any) -> Option
}