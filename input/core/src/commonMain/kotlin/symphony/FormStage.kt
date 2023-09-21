@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kotlin.js.JsExport

interface FormStage : FormInfo {
    val fields: Fields<*>

    val onNext: (() -> Unit)? get() = null

    val onPrev: (() -> Unit)? get() = null
}