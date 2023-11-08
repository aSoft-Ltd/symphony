@file:JsExport

package symphony

import kollections.JsExport

data class MultiFormStageState<out S>(
    val current: S,
    val isFirst: Boolean,
    val isLast: Boolean,
)