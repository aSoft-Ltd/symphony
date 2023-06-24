@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package symphony

import kollections.List
import kotlin.js.JsExport

data class InputState<out O, out R>(
    val phase: InputPhase<O, R>,
    val hidden: Boolean
) {
    val isSubmitting get() = phase is SubmittingPhase
}

sealed interface InputPhase<out O, out R> {
    val asCapturing get() = this as? CapturingPhase
    val asValidating get() = this as? ValidatingPhase
    val asSubmitting get() = this as? SubmittingPhase
    val asSuccess get() = this as? SuccessPhase
    val asFailure get() = this as? FailurePhase
}

object CapturingPhase : InputPhase<Nothing, Nothing>

data class ValidatingPhase<out O>(
    val output: O
) : InputPhase<O, Nothing>

data class SubmittingPhase<out O>(
    val output: O
) : InputPhase<O, Nothing>

data class SuccessPhase<out O, out R>(
    val output: O,
    val result: R
) : InputPhase<O, R>

data class FailurePhase<out O>(
    val output: O,
    val reasons: List<String>
) : InputPhase<O, Nothing>
