@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kotlinx.JsExport
import kotlinx.JsExportIgnore

interface ListField<E> : Field<List<E>, ListFieldState<E>>, ListFieldState<E> {
    fun add(item: E)

    @JsExportIgnore
    fun addAll(items: List<E>)
    fun addAll(items: Array<E>)
    fun remove(item: E)

    @JsExportIgnore
    fun removeAll(items: List<E>? = null)
    fun removeAll(items: Array<E>? = null)
    fun update(item: E, updater: () -> E)
}