package symphony.internal.validators

import cinematic.Live
import cinematic.MutableLive
import symphony.Data
import symphony.InputFieldState
import symphony.validation.Invalid
import symphony.validation.Valid
import symphony.validation.ValidationResult

class TextValidator(
    override val data: Live<Data<String>>,
    override val feedback: MutableLive<InputFieldState>,
    private val label: String,
    private val isRequired: Boolean,
    private val maxLength: Int?,
    private val minLength: Int?
) : AbstractValidator<String>(feedback) {
    override fun validate(value: String?): ValidationResult {
        if (isRequired && value.isNullOrBlank()) {
            return Invalid(IllegalArgumentException("$label is required"))
        }
        val max = maxLength
        if (max != null && value != null && value.length > max) {
            return Invalid(IllegalArgumentException("$label must have less than $max characters"))
        }
        val min = minLength
        if (min != null && value != null && value.length < min) {
            return Invalid(IllegalArgumentException("$label must have more than $min characters"))
        }
        return Valid
    }
}