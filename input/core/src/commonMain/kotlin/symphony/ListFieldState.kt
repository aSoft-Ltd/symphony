@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kollections.MutableList
import kotlinx.JsExport

interface ListFieldState<E> : FieldState<List<E>> {
    override val output: MutableList<E>
}