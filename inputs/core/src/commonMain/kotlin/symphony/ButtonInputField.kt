@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import symphony.properties.Labeled
import kotlin.js.JsExport

data class ButtonInputField(
    override val name: String,
    override val label: Label = Label(name, isRequired = true)
) : InputField, Labeled