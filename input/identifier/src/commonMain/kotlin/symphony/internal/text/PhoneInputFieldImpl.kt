package symphony.internal.text

import geo.Country
import symphony.Label
import symphony.Option
import symphony.PhoneInputField
import symphony.SingleChoiceInputField
import symphony.internal.validators.CompoundValidator
import symphony.internal.validators.LambdaValidator
import symphony.internal.validators.RequirementValidator
import symphony.internal.validators.TextValidator
import symphony.validators.PhoneValidator

@PublishedApi
internal class PhoneInputFieldImpl(
    override val name: String,
    override val isRequired: Boolean = false,
    override val label: Label = Label(name, isRequired),
    override val hint: String = label.capitalizedWithAstrix(),
    override val isReadonly: Boolean = false,
    val dialingCode: Country? = null,
    maxLength: Int? = null,
    minLength: Int? = null,
    value: String? = null,
    validator: ((String?) -> Unit)? = null,
) : AbstractBasicTextInputField(value), PhoneInputField {

    override val cv = CompoundValidator(
        data, feedback,
        RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        TextValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired, maxLength, minLength),
        PhoneValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        LambdaValidator(data, feedback, validator)
    )

    override val code = SingleChoiceInputField(
        name = "$name-dialing-code",
        items = Country.values().toList(),
        mapper = { Option(it.name, it.dialingCode) },
        serializer = Country.serializer(),
        label = "$name code",
        hint = "+255",
        isReadonly = isReadonly,
        value = dialingCode
    )
}