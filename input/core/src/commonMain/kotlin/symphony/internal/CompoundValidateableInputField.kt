package symphony.internal

import cinematic.mutableLiveOf
import symphony.InputFieldState
import symphony.internal.validators.AbstractValidator
import symphony.internal.validators.CompoundValidator

abstract class CompoundValidateableInputField<O> : AbstractValidator<O>(mutableLiveOf(InputFieldState.Empty)) {
    abstract val cv: CompoundValidator<O>
    override fun validate(value: O?) = cv.validate(value)
    override fun validateSettingInvalidsAsErrors(value: O?) = cv.validateSettingInvalidsAsErrors(value)
    override fun validateSettingInvalidsAsWarnings(value: O?) = cv.validateSettingInvalidsAsWarnings(value)
}