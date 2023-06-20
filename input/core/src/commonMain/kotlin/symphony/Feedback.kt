@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kotlin.js.JsExport

class Feedbacks(val items: List<Feedback>) {
    val errors by lazy { items.filterIsInstance<Error>().map { it.message } }
    val warnings by lazy { items.filterIsInstance<Warning>().map { it.message } }
}

sealed interface Feedback {
    val message: String
    val asWarning get() = this as? Warning
    val asError get() = this as? Error
}

data class Warning(override val message: String) : Feedback
data class Error(override val message: String) : Feedback