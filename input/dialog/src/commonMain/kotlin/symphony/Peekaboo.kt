@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.Live
import kotlin.js.JsExport

interface Peekaboo<in P, out T> {
    val state: Live<PeekabooState<T>>
    fun show(params: P)
    fun hide()
}