@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport

interface FormStage : FormInfo {
    val fields: Fields<*>
}