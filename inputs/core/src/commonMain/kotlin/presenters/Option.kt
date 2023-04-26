@file:JsExport

package presenters

import kotlin.js.JsExport

@Deprecated("use symphony")
data class Option(
    val label: String,
    val value: String = label,
    val selected: Boolean = false
)