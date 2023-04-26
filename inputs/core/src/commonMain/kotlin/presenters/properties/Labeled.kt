@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.properties

import presenters.Label
import kotlin.js.JsExport

@Deprecated("use symphony")
interface Labeled {
    val label: Label
}