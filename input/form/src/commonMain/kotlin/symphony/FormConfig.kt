@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import lexi.Logable
import kotlin.js.JsExport

interface FormConfig : Logable {
    val exitOnSubmitted: Boolean
}