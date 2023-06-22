@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kotlin.js.JsExport

interface Stages<I : Input<*>> {
    val all: List<I>
    val currentStage: I

    val step get() = all.indexOf(currentStage)
    val total get() = all.size

    val progressInPercentage get() = (100 * step) / total
}