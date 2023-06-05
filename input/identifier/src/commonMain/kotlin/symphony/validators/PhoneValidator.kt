package symphony.validators

import cinematic.MutableLive
import symphony.DataFormatted
import symphony.InputFieldState
import symphony.PhoneNumber
import symphony.internal.validators.AbstractValidator
import symphony.validation.Invalid
import symphony.validation.Valid
import symphony.validation.ValidationResult

class PhoneValidator(
    override val data: MutableLive<DataFormatted<String, PhoneNumber>>,
    feedback: MutableLive<InputFieldState>,
    private val label: String,
    private val isRequired: Boolean,
) : AbstractValidator<PhoneNumber>(feedback) {
    override fun validate(value: PhoneNumber?): ValidationResult {
        if (isRequired && value == null) return Invalid(IllegalArgumentException("$label is required"))
        if (value == null) return Valid
        return Valid
    }

    companion object {
        const val UNIVERSAL_MINIMUM_LENGTH = 9
        const val UNIVERSAL_MAXIMUM_LENGTH = 12
    }
}