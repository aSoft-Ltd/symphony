@file:JsExport

package symphony.validation

import kotlin.js.JsExport

data class Invalid(val cause: Throwable) : ValidationResult