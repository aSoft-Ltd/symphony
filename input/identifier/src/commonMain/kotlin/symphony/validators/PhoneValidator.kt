package symphony.validators

import identifier.Phone
import cinematic.MutableLive
import symphony.InputFieldState
import symphony.Data

class PhoneValidator(
    override val data: MutableLive<Data<String>>,
    feedback: MutableLive<InputFieldState>,
    label: String,
    isRequired: Boolean,
) : IdentifierValidator(feedback, label, isRequired) {
    override fun validate(value: String?) = purifyThen(value) {
        Phone(it)
    }
}