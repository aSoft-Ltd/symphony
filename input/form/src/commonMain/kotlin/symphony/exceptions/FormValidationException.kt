@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony.exceptions

import kollections.List
import symphony.LiveData
import kotlin.js.JsExport

class FormValidationException(
    override val message: String,
    val errors: String,
    val fields: List<LiveData<*>>
) : Throwable()