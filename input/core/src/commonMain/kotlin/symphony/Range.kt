@file:JsExport

package symphony

import kotlinx.serialization.Serializable
import kotlinx.JsExport

@Serializable
data class Range<out T>(
    val start: T,
    val end: T
)