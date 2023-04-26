package symphony.internal.validators

import cinematic.Live
import cinematic.MutableLive
import symphony.Data
import symphony.InputFieldState
import symphony.validation.Invalid
import symphony.validation.Valid
import symphony.validation.ValidationResult

class ClippingValidator<C : Comparable<C>>(
    override val data: Live<Data<C>>,
    override val feedback: MutableLive<InputFieldState>,
    private val label: String,
    private val max: C?,
    private val min: C?
) : AbstractValidator<C>(feedback) {
    override fun validate(value: C?): ValidationResult {
        val tag = label

        val mx = max
        if (mx != null && value != null && value > mx) {
            return Invalid(IllegalArgumentException("$tag must be before $mx"))
        }

        val mn = min
        if (mn != null && value != null && value < mn) {
            return Invalid(IllegalArgumentException("$tag must be after $mn"))
        }

        return Valid
    }
}