@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package symphony

import cinematic.Live
import kase.ExecutorState
import koncurrent.Later
import koncurrent.awaited.then
import koncurrent.awaited.andThen
import koncurrent.awaited.andZip
import koncurrent.awaited.zip
import koncurrent.awaited.catch
import kotlinx.JsExport

@Deprecated("In favour of symphony.Confirm")
interface ConfirmationBox {
    val heading: String
    val details: String

    val state: Live<ExecutorState<Unit>>

    fun onCancel(handler: () -> Unit)

    fun exit()
    fun confirm(): Later<Any?>
}