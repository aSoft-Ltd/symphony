@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package presenters.cards

import kotlinx.serialization.Serializable
import kotlinx.JsExport

@Deprecated("use symphony instead")
@Serializable
data class ValueCard<out T>(
    val title: String,
    val value: T,
    val details: String,
    val priority: Int = -1
)