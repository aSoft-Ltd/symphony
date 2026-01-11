@file:JsExport

package symphony

import kotlinx.JsExport

sealed interface ChoiceField<out O> {
    val items: Collection<O & Any>
    val mapper: (O & Any) -> Option

    fun replaceItems(items: Collection<O & Any>)
}