@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kotlin.js.JsExport

interface ListField<E> : Field<List<E>, ListFieldState<E>>, ListFieldState<E> {
    fun add(item: E)
    fun addAll(items: List<E>)
    fun remove(item: E)
    fun removeAll(items: List<E>? = null)
    fun update(item: E, updater: () -> E)
}