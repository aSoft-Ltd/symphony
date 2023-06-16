@file:Suppress("NOTHING_TO_INLINE")

package symphony

import cinematic.WatchMode
import cinematic.watch
import kash.Currency
import kash.Money
import kash.MoneyFormatter
import symphony.internal.DEFAULT_FORMATTER
import symphony.internal.MoneyInputFieldImpl
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty

inline fun MoneyInputField(
    name: String,
    isRequired: Boolean = false,
    label: String = name,
    hint: String = label,
    value: Money? = null,
    currency: Currency? = value?.currency,
    formatter: MoneyFormatter? = DEFAULT_FORMATTER,
    selectCurrency: Boolean = true,
    isReadonly: Boolean = false,
    maxAmount: Double? = null,
    minAmount: Double? = null,
    stepAmount: Double? = null,
    noinline validator: ((Money?) -> Unit)? = null
): MoneyInputField = MoneyInputFieldImpl(
    name = name,
    isRequired = isRequired,
    label = Label(label, isRequired),
    hint = hint,
    value = value,
    fixedCurrency = currency,
    selectCurrency = selectCurrency,
    formatter = formatter,
    isReadonly = isReadonly,
    maxAmount = maxAmount,
    minAmount = minAmount,
    stepAmount = stepAmount,
    validator = validator,
)

inline fun Fields<*>.money(
    name: String,
    isRequired: Boolean = false,
    label: String = name,
    hint: String = label,
    value: Money? = null,
    currency: Currency? = value?.currency,
    formatter: MoneyFormatter? = DEFAULT_FORMATTER,
    selectCurrency: Boolean = true,
    isReadonly: Boolean = false,
    maxAmount: Double? = null,
    minAmount: Double? = null,
    stepAmount: Double? = null,
    noinline validator: ((Money?) -> Unit)? = null
): MoneyInputField = getOrCreate(name) {
    MoneyInputField(name, isRequired, label, hint, value, currency, formatter, selectCurrency, isReadonly, maxAmount, minAmount, stepAmount, validator)
}

inline fun Fields<*>.money(
    name: KMutableProperty0<Money?>,
    isRequired: Boolean = false,
    label: String = name.name,
    hint: String = label,
    value: Money? = name.get(),
    currency: Currency? = value?.currency,
    formatter: MoneyFormatter? = DEFAULT_FORMATTER,
    selectCurrency: Boolean = true,
    isReadonly: Boolean = false,
    maxAmount: Double? = null,
    minAmount: Double? = null,
    stepAmount: Double? = null,
    noinline validator: ((Money?) -> Unit)? = null
) = money(name.name, isRequired, label, hint, value, currency, formatter, selectCurrency, isReadonly, maxAmount, minAmount, stepAmount, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.set(it.output) }
}

inline fun Fields<*>.money(
    name: KMutableProperty0<Money>,
    isRequired: Boolean = false,
    label: String = name.name,
    hint: String = label,
    value: Money = name.get(),
    currency: Currency? = value?.currency,
    formatter: MoneyFormatter? = DEFAULT_FORMATTER,
    selectCurrency: Boolean = true,
    isReadonly: Boolean = false,
    maxAmount: Double? = null,
    minAmount: Double? = null,
    stepAmount: Double? = null,
    noinline validator: ((Money?) -> Unit)? = null
) = money(name.name, isRequired, label, hint, value, currency, formatter, selectCurrency, isReadonly, maxAmount, minAmount, stepAmount, validator).apply {
    data.watch(mode = WatchMode.Casually) { name.setIfNotNull(it.output) }
}