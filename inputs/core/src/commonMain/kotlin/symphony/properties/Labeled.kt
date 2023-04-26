@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.properties

import presenters.Label
import kotlin.js.JsExport

interface Labeled {
    val label: Label
}