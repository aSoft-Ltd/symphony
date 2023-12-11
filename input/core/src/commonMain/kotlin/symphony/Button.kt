@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import symphony.properties.Labeled
import kotlinx.JsExport

data class Button(
    val name: String,
    override val label: Label = Label(name, isRequired = true)
) : Labeled