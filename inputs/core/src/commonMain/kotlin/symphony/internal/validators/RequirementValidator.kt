package symphony.internal.validators

import cinematic.Live
import cinematic.MutableLive
import symphony.Data
import symphony.InputFieldState
import symphony.validation.Invalid
import symphony.validation.Valid
import symphony.validation.ValidationResult

class RequirementValidator<T>(
    override val data: Live<Data<T>>,
    override val feedback: MutableLive<InputFieldState>,
    private val label: String,
    private val isRequired: Boolean,
) : AbstractValidator<T>(feedback) {

    override fun validate(value: T?): ValidationResult {
        val message = IllegalArgumentException("$label is required")
        if (isRequired && value == null) {
            return Invalid(message)
        }

        if (isRequired && value is Collection<Any?> && value.isEmpty()) {
            return Invalid(message)
        }
        return Valid
    }
}