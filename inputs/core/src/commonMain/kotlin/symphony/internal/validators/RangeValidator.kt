package symphony.internal.validators

import cinematic.Live
import cinematic.MutableLive
import symphony.Data
import symphony.Range
import symphony.InputFieldState
import symphony.validation.Invalid
import symphony.validation.Valid
import symphony.validation.ValidationResult

class RangeValidator<C : Comparable<C>>(
    override val data: Live<Data<Range<C>>>,
    override val feedback: MutableLive<InputFieldState>,
    private val isRequired: Boolean,
    private val label: String,
    private val max: C?,
    private val min: C?
) : AbstractValidator<Range<C>>(feedback) {

    override fun validate(value: Range<C>?) = validate(value?.start, value?.end)

    private fun validate(start: C?, end: C?): ValidationResult {
        if (isRequired && start == null) {
            return Invalid(IllegalArgumentException("$label start value is required"))
        }

        if (isRequired && end == null) {
            return Invalid(IllegalArgumentException("$label end value is required"))
        }

        if (start == null && end == null) return Valid

        if (start != null && end == null) {
            return Invalid(IllegalArgumentException("$label end is required"))
        }

        if (start == null && end != null) {
            return Invalid(IllegalArgumentException("$label start is required"))
        }

        val b = start!!
        val mx = max

        if (mx != null && b > mx) {
            return Invalid(IllegalArgumentException("$label must be before/less than $mx"))
        }

        val e = end!!
        val min = min
        if (min != null && e < min) {
            return Invalid(IllegalArgumentException("$label must be after/greater than $min"))
        }

        if (b > e) {
            return Invalid(IllegalArgumentException("$label can't range from $start to $end"))
        }

        return Valid
    }
}