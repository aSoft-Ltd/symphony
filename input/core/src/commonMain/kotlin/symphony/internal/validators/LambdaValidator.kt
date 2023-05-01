package symphony.internal.validators

import cinematic.Live
import cinematic.MutableLive
import symphony.Data
import symphony.InputFieldState
import symphony.validation.Invalid
import symphony.validation.Valid
import symphony.validation.ValidationResult

class LambdaValidator<T>(
    override val data: Live<Data<T>>,
    override val feedback: MutableLive<InputFieldState>,
    private val lambda: ((T?) -> Unit)?
) : AbstractValidator<T>(feedback) {
    override fun validate(value: T?): ValidationResult = try {
        lambda?.invoke(value)
        Valid
    } catch (err: Throwable) {
        Invalid(err)
    }
}