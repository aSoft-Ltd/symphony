package symphony.internal

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import symphony.BooleanInputField
import symphony.Label
import symphony.internal.validators.CompoundValidator
import symphony.internal.validators.LambdaValidator
import symphony.internal.validators.RequirementValidator

@PublishedApi
internal class BooleanInputFieldImpl(
    override val name: String,
    override val isRequired: Boolean,
    override val label: Label,
    override val hint: String,
    override val isReadonly: Boolean,
    value: Boolean?,
    validator: ((Boolean?) -> Unit)?,
) : PlainDataField<Boolean>(value), BooleanInputField {
    override val serializer: KSerializer<Boolean> = Boolean.serializer()

    override val cv = CompoundValidator(
        data, feedback,
        RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        LambdaValidator(data, feedback, validator)
    )

    override fun toggle() = set(!(data.value.output ?: false))
}