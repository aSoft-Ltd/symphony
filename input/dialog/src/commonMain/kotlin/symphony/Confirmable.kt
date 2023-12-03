@file:JsExport

package symphony

import cinematic.MutableLive
import kotlin.js.JsExport

@Suppress("NON_EXPORTABLE_TYPE")
@Deprecated("In favour of symphony.Confirm")
interface Confirmable {
    val confirm: MutableLive<ConfirmationBox?>
    fun hideConfirmationBox()
}