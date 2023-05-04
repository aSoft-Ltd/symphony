@file:JsExport

package symphony

import kollections.JsExport
import kotlinx.serialization.Serializable

@Serializable
enum class View {
    Table,
    List
}