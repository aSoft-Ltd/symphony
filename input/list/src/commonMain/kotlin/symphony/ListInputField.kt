@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import symphony.properties.Settable
import symphony.validation.Validateable
import kotlin.js.JsExport

interface ListInputField<E> : InputField, CommonInputProperties, LiveDataList<E>, Settable<List<E>>, Validateable<List<E>> {
    val minItems: Int?
    val maxItems: Int?
    fun add(item: E)
    fun addAll(items: List<E>)
    fun remove(item: E)
    fun removeAll(items: List<E> = data.value.output)
    fun update(item: E, updater: () -> E)
}