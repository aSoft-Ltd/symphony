package symphony.internal.text

import symphony.Label
import symphony.TextInputField
import symphony.internal.validators.CompoundValidator
import symphony.internal.validators.LambdaValidator
import symphony.internal.validators.RequirementValidator
import symphony.internal.validators.TextValidator

@PublishedApi
internal class TextInputFieldImpl(
    override val name: String,
    override val isRequired: Boolean,
    override val label: Label,
    override val hint: String,
    override val isReadonly: Boolean,
    override val maxLength: Int?,
    override val minLength: Int?,
    value: String?,
    validator: ((String?) -> Unit)?,
) : AbstractBasicTextInputField(value), TextInputField {
    override val cv = CompoundValidator(
        data, feedback,
        RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        TextValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired, maxLength, minLength),
        LambdaValidator(data, feedback, validator)
    )
}