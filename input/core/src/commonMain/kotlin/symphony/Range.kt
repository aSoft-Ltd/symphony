@file:JsExport

package symphony

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
data class Range<out T>(
    val start: T,
    val end: T
)