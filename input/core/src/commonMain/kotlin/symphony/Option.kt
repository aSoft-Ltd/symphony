@file:JsExport
@file:Suppress("EqualsOrHashCode")

package symphony

import kotlin.js.JsExport

data class Option(
    val label: String,
    val value: String = label,
    val selected: Boolean = false
) {
    override fun equals(other: Any?) = other is Option && other.value == value
}