@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.Live
import koncurrent.Later
import koncurrent.awaited.then
import koncurrent.awaited.andThen
import koncurrent.awaited.andZip
import koncurrent.awaited.zip
import koncurrent.awaited.catch
import kotlinx.JsExport

interface Confirm<in P> {
    val state: Live<ConfirmState<@UnsafeVariance P>>

    val actions: FormActions
    fun hide()

    fun show(params: P)
    fun confirm(): Later<Any?>
}