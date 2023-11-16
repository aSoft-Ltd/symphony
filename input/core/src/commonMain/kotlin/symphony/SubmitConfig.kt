@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import lexi.Logable
import kotlin.js.JsExport

@Deprecated("Do not use this anymore")
interface SubmitConfig : Logable {
    val exitOnSuccess: Boolean
}