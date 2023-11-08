@file:JsExport

package symphony

import kollections.JsExport

data class MultiStageFormStageProgress(
    val step: Int,
    val total: Int
) {
    val percentage get() = (step * 100) / total
}