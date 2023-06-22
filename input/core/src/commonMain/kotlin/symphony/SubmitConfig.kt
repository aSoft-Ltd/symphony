@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import lexi.Logable
import kotlin.js.JsExport

interface SubmitConfig : Logable {
    val exitOnSuccess: Boolean
}