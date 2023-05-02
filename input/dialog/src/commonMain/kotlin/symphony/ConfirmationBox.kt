@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "WRONG_EXPORTED_DECLARATION")

package symphony

import kevlar.MutableAction0
import kase.ExecutorState
import koncurrent.Later
import cinematic.Live
import kotlin.js.JsExport

interface ConfirmationBox {
    val heading: String
    val details: String

    val state: Live<ExecutorState<Unit>>

    val cancel: MutableAction0<Unit>

    fun exit()
    fun confirm(): Later<Any?>
}