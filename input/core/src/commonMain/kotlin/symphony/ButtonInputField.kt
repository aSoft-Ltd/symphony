@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import symphony.properties.Labeled
import kotlin.js.JsExport

data class ButtonInputField(
    val name: String,
    override val label: Label = Label(name, isRequired = true)
) : Labeled