package symphony.internal.utils

import cinematic.MutableLive
import symphony.InputFieldState
import symphony.validation.Invalid
import symphony.validation.Valid
import symphony.validation.ValidationResult

abstract class FeedbackSetter(private val feedback: MutableLive<InputFieldState>) {

    private fun setFeedbacks(res: ValidationResult, body: (res: Invalid) -> InputFieldState): ValidationResult {
        feedback.value = when (res) {
            is Invalid -> body(res)
            is Valid -> InputFieldState.Empty
        }
        return res
    }

    fun setWarning(message: String, cause: Throwable) {
        feedback.value = InputFieldState.Warning(message, cause)
    }

    fun setFeedbacksAsWarnings(res: ValidationResult): ValidationResult {
        setFeedbacks(res) { InputFieldState.Warning(it.cause.message ?: "Unknown", it.cause) }
        return res
    }

    fun setFeedbacksAsErrors(res: ValidationResult): ValidationResult {
        setFeedbacks(res) { InputFieldState.Error(it.cause.message ?: "Unknown", it.cause) }
        return res
    }
}