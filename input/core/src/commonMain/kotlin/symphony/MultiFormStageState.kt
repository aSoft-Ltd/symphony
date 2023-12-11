@file:JsExport

package symphony

import kotlinx.JsExport

data class MultiFormStageState<out S>(
    val current: S,
    val isFirst: Boolean,
    val isLast: Boolean,
)