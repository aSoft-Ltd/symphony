package symphony.internal.text

import cinematic.watch
import geo.Country
import symphony.Formatter
import symphony.Label
import symphony.LongInputField
import symphony.NumberInputField
import symphony.Option
import symphony.PhoneInputField
import symphony.PhoneNumber
import symphony.SingleChoiceInputField
import symphony.internal.FormattedData
import symphony.internal.TransformedDataField
import symphony.internal.utils.DataTransformer
import symphony.internal.validators.CompoundValidator
import symphony.internal.validators.LambdaValidator
import symphony.internal.validators.RequirementValidator
import symphony.validation.Invalid
import symphony.validation.ValidationResult
import symphony.validators.PhoneValidator

@PublishedApi
internal class PhoneInputFieldImpl(
    override val name: String,
    override val isRequired: Boolean = false,
    override val label: Label = Label(name, isRequired),
    override val hint: String = label.capitalizedWithAstrix(),
    override val isReadonly: Boolean = false,
    dialingCode: Country? = null,
    value: String? = null,
    validator: ((PhoneNumber?) -> Unit)? = null,
) : TransformedDataField<String, PhoneNumber>(phoneNumber(dialingCode, value)), PhoneInputField {

    override val cv = CompoundValidator(
        data, feedback,
        RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        PhoneValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        LambdaValidator(data, feedback, validator)
    )

    override fun validate(value: PhoneNumber?): ValidationResult {
        var res: ValidationResult = code.validate()
        if (res is Invalid) return res
        res = number.validate()
        if (res is Invalid) return res
        return cv.validate(value)
    }

    private val formatter = Formatter<PhoneNumber> { it?.toString() }

    override fun type(text: String) = number.type(text)

    override fun set(value: String?) = number.set(value)

    override val transformer: DataTransformer<String, PhoneNumber> = DataTransformer(formatter) {
        val country = code.output ?: return@DataTransformer null
        val body = number.output ?: return@DataTransformer null
        PhoneNumber(country, body)
    }

    override val output: PhoneNumber? get() = phoneNumber(code.output, number.output?.toString())

    override val number: NumberInputField<Long> = LongInputField(
        name = "$name-number",
        label = "$name number",
        hint = "7XX YYY ZZZ",
        isReadonly = isReadonly,
        isRequired = isRequired,
        min = "1${"0".repeat(PhoneValidator.UNIVERSAL_MINIMUM_LENGTH - 1)}".toLong(),
        max = "9".repeat(PhoneValidator.UNIVERSAL_MAXIMUM_LENGTH).toLong(),
        value = value?.toLong()
    )

    override val code = SingleChoiceInputField(
        name = "$name-dialing-code",
        items = Country.values().toList(),
        mapper = { Option(it.name, it.dialingCode) },
        label = "$name code",
        isReadonly = isReadonly,
        isRequired = isRequired,
        value = dialingCode
    )

    init {
        watch(code.data, number.data) { cd, bd ->
            val pn = phoneNumber(cd.output, bd.output?.toString())
            data.value = FormattedData(null, pn?.toString() ?: "", pn)
        }
    }

    companion object {
        private fun phoneNumber(code: Country?, number: String?): PhoneNumber? {
            if (code == null) return null
            val body = number?.toLongOrNull() ?: return null
            return PhoneNumber(code, body)
        }
    }
}