@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.exceptions

import kollections.List
import kotlin.js.JsExport

class FormFieldValidationException(
    override val message: String,
    val reasons: List<String>
) : Throwable(message)