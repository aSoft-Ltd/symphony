package symphony.internal.text

import symphony.Label
import symphony.internal.AbstractBasicTextInputField
import symphony.internal.validators.CompoundValidator
import symphony.internal.validators.LambdaValidator
import symphony.internal.validators.RequirementValidator
import symphony.internal.validators.TextValidator
import symphony.validators.EmailValidator

@PublishedApi
internal class EmailInputFieldImpl(
    override val name: String,
    override val isRequired: Boolean = false,
    override val label: Label = Label(name, isRequired),
    override val hint: String = label.capitalizedWithAstrix(),
    override val isReadonly: Boolean = false,
    maxLength: Int? = null,
    minLength: Int? = null,
    value: String? = null,
    validator: ((String?) -> Unit)? = null,
) : AbstractBasicTextInputField(value) {
    override val cv = CompoundValidator(
        data, feedback,
        RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        TextValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired, maxLength, minLength),
        EmailValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        LambdaValidator(data, feedback, validator)
    )
}