package symphony.internal.numbers

import liquid.NumberFormatter
import symphony.Label
import symphony.NumberInputField
import symphony.internal.TransformedDataField
import symphony.internal.utils.Typer
import symphony.internal.validators.ClippingValidator
import symphony.internal.validators.CompoundValidator
import symphony.internal.validators.LambdaValidator
import symphony.internal.validators.RequirementValidator

@PublishedApi
internal abstract class AbstractNumberInputField<N>(
    name: String,
    isRequired: Boolean,
    label: Label,
    hint: String,
    isReadonly: Boolean,
    max: N?,
    min: N?,
    step: N?,
    formatter: NumberFormatter?,
    value: N?,
    validator: ((N?) -> Unit)?
) : TransformedDataField<String, N>(value), NumberInputField<N> where N : Comparable<N>, N : Number {
    override val cv: CompoundValidator<N> by lazy {
        CompoundValidator(
            data, feedback,
            RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
            ClippingValidator(data, feedback, label.capitalizedWithoutAstrix(), max, min),
            LambdaValidator(data, feedback, validator)
        )
    }

    override fun type(text: String) = Typer(data.value.input, setter).type(text)

    override fun set(double: Double) = setter.set(double.toString())

    override fun set(integer: Int) = setter.set(integer.toString())
}