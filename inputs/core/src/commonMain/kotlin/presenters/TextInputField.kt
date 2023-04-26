@file:JsExport

package presenters

import kotlin.js.JsExport

@Deprecated("use symphony")
interface TextInputField : BasicTextInputField {
    val maxLength: Int?
    val minLength: Int?
}