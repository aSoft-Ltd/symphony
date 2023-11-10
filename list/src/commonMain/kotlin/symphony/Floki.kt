@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.Flock
import kotlin.js.JsExport
import kollections.MutableFlock

class Floki(
    val numbers: Flock<Int>,
    val mutableNumbers: MutableFlock<Int>
)