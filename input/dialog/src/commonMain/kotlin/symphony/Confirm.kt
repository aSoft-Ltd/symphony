@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.Live
import koncurrent.Later
import koncurrent.later.then
import koncurrent.later.andThen
import koncurrent.later.andZip
import koncurrent.later.zip
import koncurrent.later.catch
import kotlinx.JsExport

interface Confirm<in P> {
    val state: Live<ConfirmState<@UnsafeVariance P>>

    val actions: FormActions
    fun hide()

    fun show(params: P)
    fun confirm(): Later<Any?>
}