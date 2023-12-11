@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kollections.toIList
import kotlinx.JsExport

class Feedbacks(val items: List<Feedback>) {
    val errors by lazy { items.filterIsInstance<ErrorFeedback>().map { it.message }.toIList() }
    val warnings by lazy { items.filterIsInstance<WarningFeedback>().map { it.message }.toIList() }
}

sealed interface Feedback {
    val message: String
    val asWarning get() = this as? WarningFeedback
    val asError get() = this as? ErrorFeedback
}

data class WarningFeedback(override val message: String) : Feedback
data class ErrorFeedback(override val message: String) : Feedback