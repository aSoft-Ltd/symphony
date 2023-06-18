@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kotlin.js.JsExport

data class Feedbacks(val items: List<Feedback>) {
    val errors by lazy { items.filterIsInstance<Error>() }
    val warnings by lazy { items.filterIsInstance<Warning>() }
}

sealed interface Feedback {
    val asWarning get() = this as? Warning
    val asError get() = this as? Error
}

data class Warning(val message: String, val cause: Throwable) : Feedback
data class Error(val message: String, val cause: Throwable) : Feedback