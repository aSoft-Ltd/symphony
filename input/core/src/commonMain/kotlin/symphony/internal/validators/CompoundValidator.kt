package symphony.internal.validators

import cinematic.Live
import cinematic.MutableLive
import symphony.Data
import symphony.InputFieldState
import symphony.validation.Invalid
import symphony.validation.Valid
import symphony.validation.ValidationResult

class CompoundValidator<T>(
    override val data: Live<Data<T>>,
    override val feedback: MutableLive<InputFieldState>,
    vararg valigators: AbstractValidator<T>
) : AbstractValidator<T>(feedback) {
    private val validators = valigators

    override fun validate(value: T?): ValidationResult {
        var res: ValidationResult = Valid
        for (validator in validators) {
            res = validator.validate(value)
            if (res is Invalid) break
        }
        return res
    }
}