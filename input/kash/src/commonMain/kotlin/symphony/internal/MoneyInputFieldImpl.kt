package symphony.internal

import kash.Currency
import kash.ISO3CurrencySerializer
import kash.Money
import kash.Monetary
import kash.MoneyFormatter
import kash.serializers.MoneySerializer
import kollections.toIList
import kotlinx.serialization.KSerializer
import cinematic.watch
import symphony.Label
import symphony.MonetaryInputField
import symphony.MoneyInputField
import symphony.Option
import symphony.SingleChoiceInputField
import symphony.internal.utils.DataTransformer
import symphony.internal.utils.Typer
import symphony.internal.validators.ClippingValidator
import symphony.internal.validators.CompoundValidator
import symphony.internal.validators.LambdaValidator
import symphony.internal.validators.RequirementValidator
import symphony.validation.Invalid
import symphony.validation.Valid
import symphony.validation.ValidationResult

@PublishedApi
internal class MoneyInputFieldImpl(
    override val name: String,
    override val isRequired: Boolean,
    override val label: Label,
    override val hint: String,
    private val value: Money?,
    private val fixedCurrency: Currency?,
    private val selectCurrency: Boolean,
    override val isReadonly: Boolean,
    private val formatter: MoneyFormatter?,
    private val maxAmount: Double?,
    private val minAmount: Double?,
    private val stepAmount: Double?,
    private val validator: ((Money?) -> Unit)?
) : TransformedDataField<String, Money>(value), MoneyInputField {

    override val transformer: DataTransformer<String, Money> get() = error("Don't transform directly")

    private val theCurrency get() = fixedCurrency ?: value?.currency

    override val currency = SingleChoiceInputField(
        name = "$name-currency",
        isRequired = isRequired,
        label = "$name currency",
        isReadonly = !selectCurrency,
        items = Currency.values.toIList(),
        mapper = { Option(it.name, it.name) },
        serializer = ISO3CurrencySerializer,
        value = theCurrency
    )

    override val amount = MonetaryInputField(
        name = "$name-amount",
        isRequired = isRequired,
        label = "$name amount",
        hint = hint,
        value = value?.toMonetary(),
        isReadonly = isReadonly,
        formatter = formatter,
        maxAmount = maxAmount?.let { Monetary(it) },
        minAmount = minAmount?.let { Monetary(it) },
        stepAmount = stepAmount,
        validator = null
    )

    private fun money(amount: Double?): Money? {
        val cur = theCurrency ?: return null
        val a = amount ?: return null
        return Money(a, cur)
    }

    override val cv = CompoundValidator(
        data, feedback,
        RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        ClippingValidator(data, feedback, label.capitalizedWithoutAstrix(), money(maxAmount), money(minAmount)),
        LambdaValidator(data, feedback, validator)
    )

    init {
        watch(this.currency.data, this.amount.data) { cur, amm ->
            val c = cur.output ?: Currency.UXX
            val a = amm.output ?: return@watch
            val money = a.with(c)
            val text = formatter?.format(money) ?: money.toFormattedString()
            data.value = FormattedData("${c.globalSymbol} $a", text, money)
        }
    }

    override fun validate(value: Money?): ValidationResult {
        arrayOf(
            currency.validate(value?.currency),
            amount.validate(value?.toMonetary())
        ).forEach { res ->
            if (res is Invalid) return res
        }
        return Valid
    }


    override fun type(text: String) = Typer(default.input, amount).type(text)

    override fun set(value: String?) = amount.set(value)

    override fun setAmount(number: Int?) = amount.setAmount(number)

    override val serializer: KSerializer<Money> = MoneySerializer

    override fun clear() {
        currency.clear()
        amount.clear()
        super.clear()
    }

    override fun setAmount(value: String?) = amount.setAmount(value)

    override fun setCurrency(value: String) = currency.selectValue(value)

    override fun setCurrency(currency: Currency) = this.currency.selectItem(currency)

    override fun setAmount(number: Double?) = amount.setAmount(number)
}