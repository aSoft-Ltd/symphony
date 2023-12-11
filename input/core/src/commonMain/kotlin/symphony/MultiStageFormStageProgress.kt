@file:JsExport

package symphony

import kotlinx.JsExport

data class MultiStageFormStageProgress(
    val step: Int,
    val total: Int
) {
    val percentage get() = (step * 100) / total
}