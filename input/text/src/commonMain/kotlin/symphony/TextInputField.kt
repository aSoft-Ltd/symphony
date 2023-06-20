@file:JsExport

package symphony

import kotlin.js.JsExport

@Deprecated("In favour of TextField")
interface TextInputField : BasicTextInputField {
    val maxLength: Int?
    val minLength: Int?
}