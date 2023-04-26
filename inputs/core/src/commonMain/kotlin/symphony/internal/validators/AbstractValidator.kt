package symphony.internal.validators

import cinematic.MutableLive
import symphony.InputFieldState
import symphony.internal.utils.FeedbackSetter
import symphony.validation.Validateable
import symphony.validation.ValidationResult

abstract class AbstractValidator<I>(override val feedback: MutableLive<InputFieldState>) : FeedbackSetter(feedback), Validateable<I> {

    abstract override fun validate(value: I?): ValidationResult

    override fun validateSettingInvalidsAsWarnings(value: I?) = setFeedbacksAsWarnings(validate(value))

    override fun validateSettingInvalidsAsErrors(value: I?) = setFeedbacksAsErrors(validate(value))
}