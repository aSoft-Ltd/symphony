@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import lexi.Logable
import kotlinx.JsExport

@Deprecated("Do not use this anymore")
interface SubmitConfig : Logable {
    val exitOnSuccess: Boolean
}