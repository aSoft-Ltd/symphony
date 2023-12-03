@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import cinematic.Live
import kotlin.js.JsExport

interface FormBox {
    val state: Live<FormBoxState>
}