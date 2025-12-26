@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlinx.JsExport

interface ListFieldState<E> : FieldState<List<E>> {
    override val output: MutableList<E>
}