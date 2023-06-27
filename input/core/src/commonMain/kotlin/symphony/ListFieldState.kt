@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kotlin.js.JsExport

interface ListFieldState<E> : FState<List<E>> {
    override val output: List<E>
}