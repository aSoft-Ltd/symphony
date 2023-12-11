@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package symphony

import cinematic.Live
import kase.ExecutorState
import koncurrent.Later
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