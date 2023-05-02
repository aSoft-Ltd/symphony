package symphony.validators

import cinematic.MutableLive
import symphony.InputFieldState
import symphony.Data
import symphony.internal.validators.AbstractValidator
import symphony.validation.Invalid
import symphony.validation.Valid
import symphony.validation.ValidationResult

abstract class IdentifierValidator(
    feedback: MutableLive<InputFieldState>,
    private val label: String,
    private val isRequired: Boolean,
) : AbstractValidator<String>(feedback) {

    fun purifyThen(value: String?, block: (String) -> Unit): ValidationResult {
        if (isRequired && value == null) return Invalid(IllegalArgumentException("$label is Required"))
        if (!isRequired && value == null) return Valid
        val v: String = value ?: return Invalid(IllegalStateException("$label is Required"))
        return try {
            block(v)
            Valid
        } catch (err: Throwable) {
            Invalid(err)
        }
    }
}