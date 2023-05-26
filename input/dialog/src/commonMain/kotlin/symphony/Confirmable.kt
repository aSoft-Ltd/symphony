@file:JsExport

package symphony

import cinematic.MutableLive
import kotlin.js.JsExport

@Suppress("NON_EXPORTABLE_TYPE")
interface Confirmable {
    val confirm: MutableLive<ConfirmationBox?>
    fun hideConfirmationBox()
}