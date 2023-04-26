@file:JsExport

package presenters.validation

import kotlin.js.JsExport

@Deprecated("use symphony")
data class Invalid(val cause: Throwable) : ValidationResult