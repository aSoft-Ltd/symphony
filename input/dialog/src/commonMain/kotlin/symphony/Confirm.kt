@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.Live
import koncurrent.Later
import kotlin.js.JsExport

interface Confirm<in P> {
    val state: Live<ConfirmState<@UnsafeVariance P>>

    val actions: FormActions
    fun hide()
    fun show(params: P)
    fun confirm(): Later<Any?>
}