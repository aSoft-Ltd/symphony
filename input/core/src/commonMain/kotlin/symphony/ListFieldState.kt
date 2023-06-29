@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kollections.MutableList
import kotlin.js.JsExport

interface ListFieldState<E> : FieldState<List<E>> {
    override val output: MutableList<E>
}